package com.example.templatesampleapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.templatesampleapp.R
import com.example.templatesampleapp.base.BaseFragment
import com.example.templatesampleapp.databinding.FragmentCurrencyChartsBinding
import com.example.templatesampleapp.helper.Constants
import com.example.templatesampleapp.utils.showLog
import com.example.templatesampleapp.viewmodel.MainUiEvents
import com.example.templatesampleapp.viewmodel.SharedViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


/**
 * @author Umer Bilal
 * Created 11/9/2023 at 1:18 AM
 */
class CurrencyChartFragment :
    BaseFragment<FragmentCurrencyChartsBinding>(R.layout.fragment_currency_charts) {


    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setSupportedCurrencyList()
        addObserver()
        callToBarChart()

    }

    private fun callToBarChart() {
        //fit the data into a bar
        lifecycleScope.launch {
            val data=viewModel.currencyRatesData.first()
            when (data) {
                is MainUiEvents.GotData -> {
                    val hashMap=HashMap<Float,Float>()
                    val listOfCurrency=data.data.conversion_rates
                    val eachNumberDiv=(binding.edittxtCurrency.text.toString().toIntOrNull()?:1).toDouble()/listOfCurrency.size
                    listOfCurrency.entries.forEachIndexed { index, entry ->
                        hashMap.put((index*eachNumberDiv).toFloat(),entry.value.toFloat())
                    }
                    implChartView(hashMap)
                }

                else -> {

                }
            }
        }
    }

    private fun implChartView(hashMap: HashMap<Float, Float>) {
        with(binding.barChartView) {
            animateY(1000)
            //setting animation for x-axis, the bar will pop up separately within the time we set
            animateX(1000)
            val valueList = ArrayList<Double>()
            val entries = ArrayList<BarEntry>()
            val title = "Currency Bar Chart"

            //input data
            hashMap.entries.forEach {

                val barEntry = BarEntry(it.key, it.value)
                entries.add(barEntry)
            }

            val barDataSet = BarDataSet(entries, title)
            initBarDataSet(barDataSet)

            val data = BarData(barDataSet)
            setData(data)
            invalidate()
        }
    }

    private fun initBarDataSet(barDataSet: BarDataSet) {
        //Changing the color of the bar
        barDataSet.color = Color.parseColor("#304567")
        //Setting the size of the form in the legend
        barDataSet.formSize = 15f
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false)
        //setting the text size of the value of the bar
        barDataSet.valueTextSize = 12f
    }

    private fun addObserver() {
        lifecycleScope.launchWhenStarted {
            binding.edittxtCurrency.setText(viewModel.activeCurrencyValue)
        }
        binding.edittxtCurrency.addTextChangedListener {
            callToBarChart()
        }
        binding.autocompletetxt.setOnItemClickListener { _, _, i, l ->
            showLog(" $i --- $l  ${Constants.getSupportedCountryCode(requireContext()).get(i)}","ANDROITAGLJ")
        }
    }

    private fun setSupportedCurrencyList() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            Constants.getSupportedCountryCode(requireContext())
        )
        binding.autocompletetxt.setAdapter(adapter)
    }

}