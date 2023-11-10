package com.example.templatesampleapp.ui.activmain

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.templatesampleapp.BuildConfig
import com.example.templatesampleapp.R
import com.example.templatesampleapp.adapter.holders.CurrencyFragmentAdapter
import com.example.templatesampleapp.base.BaseActivity
import com.example.templatesampleapp.databinding.ActivityMainBinding
import com.example.templatesampleapp.viewmodel.SharedViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
     val viewModel by viewModels<SharedViewModel>()

    private val viewPagerAdapter by lazy {
        CurrencyFragmentAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUiComponents()

    }

    private fun setUiComponents() {
        binding.pager.isUserInputEnabled = false

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.pager.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.pager.adapter = viewPagerAdapter
    }
}