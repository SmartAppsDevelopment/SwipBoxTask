package com.example.templatesampleapp.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<T : ViewBinding>(@LayoutRes val layoutRes: Int) : AppCompatActivity() {
    lateinit var binding: T

    //    abstract val viewModel:BaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)

    }


}