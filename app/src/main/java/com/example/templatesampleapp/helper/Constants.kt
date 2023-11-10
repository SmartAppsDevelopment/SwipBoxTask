package com.example.templatesampleapp.helper

import android.content.Context
import com.example.templatesampleapp.R

object  Constants {
    val BASE_URL="https://v6.exchangerate-api.com/v6/"


    fun getSupportedCountryCode(context: Context)=context.resources.getStringArray(R.array.countryCode).toMutableList()


}