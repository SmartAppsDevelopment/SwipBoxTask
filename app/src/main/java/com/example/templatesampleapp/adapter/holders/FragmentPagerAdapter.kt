package com.example.templatesampleapp.adapter.holders

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.templatesampleapp.ui.fragment.CurrencyChartFragment
import com.example.templatesampleapp.ui.fragment.CurrencyRatesFragment

/**
 * @author Umer Bilal
 * Created 11/9/2023 at 1:46 AM
 */
class CurrencyFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int =2

    override fun createFragment(position: Int): Fragment =when(position){
        0->CurrencyRatesFragment()
        1->CurrencyChartFragment()
        else->CurrencyRatesFragment()
    }
}