package com.example.templatesampleapp.ui.fragment

import android.os.Bundle
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.templatesampleapp.BuildConfig
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.ExchangeRvAdapter
import com.example.templatesampleapp.databinding.FragmentCurrencyratesBinding
import com.example.templatesampleapp.utils.showToast
import com.example.templatesampleapp.viewmodel.MainUiEvents
import com.example.templatesampleapp.viewmodel.ProgressEvents
import com.example.templatesampleapp.viewmodel.SharedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @author Umer Bilal
 * Created 11/9/2023 at 1:18 AM
 */
class CurrencyRatesFragment : Fragment() {


    lateinit var binding: FragmentCurrencyratesBinding
    private val viewModel by activityViewModels<SharedViewModel>()

    private val rvAdapter = ExchangeRvAdapter()

    init {
        lifecycleScope.launchWhenStarted {
            viewModel._progressEvents.consumeAsFlow().collect {
                when (it) {
                    ProgressEvents.HideProgressDialog -> {
                        binding.progressCircular.isVisible = false
                    }

                    ProgressEvents.ShowProgressDialog -> {
                        binding.progressCircular.isVisible = true
                    }
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.currencyRatesData.collect {
                when (it) {
                    is MainUiEvents.Error -> {
                        requireContext().showToast(it.msg)
                    }

                    is MainUiEvents.GotData -> {
                        with(viewModel) {
                            rvAdapter.submitList(
                                it.data.toCurrencyUiModels(
                                    activeCurrencyName,
                                    activeCurrencyValue
                                )
                            )
                        }
                    }


                    MainUiEvents.Idle -> {

                    }


                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_currencyrates, null, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDateToAdapter()
        lifecycleScope.launch {
            viewModel.getExchangeRates(BuildConfig.API_KEY, "EUR")
        }

        binding.edittxtCurrency.addTextChangedListener { curText ->
            viewModel._activeCurrencyValue.value = curText.toString()
        }
        addObserver()
        updateOnTextView()
    }

    private fun addObserver() {
        var activeJob: Job? = null
        viewModel._activeCurrencyValue.observe(viewLifecycleOwner) {
            activeJob?.cancel()
            activeJob = lifecycleScope.launch {
                viewModel.currencyRatesData.first {
                    when (it) {
                        is MainUiEvents.GotData -> {
                            val newList = it.data.toCurrencyUiModels(
                                viewModel.activeCurrencyName,
                                viewModel.activeCurrencyValue
                            )
                            rvAdapter.submitList(null)
                            rvAdapter.submitList(newList)
                        }
                    }
                    true
                }
            }
        }
    }

    private fun setDateToAdapter() {
        binding.rv.adapter = rvAdapter
        binding.edittxtCurrency.filters = arrayOf(LengthFilter(6))
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.refreshapi, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            viewModel.getExchangeRates(BuildConfig.API_KEY, viewModel.activeCurrencyName)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateOnTextView() {
        lifecycleScope.launchWhenStarted {
            binding.edittxtCurrency.setText(viewModel.activeCurrencyValue)
        }
    }

}