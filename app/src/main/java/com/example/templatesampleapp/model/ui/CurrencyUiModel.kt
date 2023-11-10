package com.example.templatesampleapp.model.ui

import com.example.templatesampleapp.utils.roundToOneDecimalPlace
import com.example.templatesampleapp.utils.showLog

/**
 * @author Umer Bilal
 * Created 11/9/2023 at 2:05 AM
 */
data class CurrencyUiModel(
    val currentCurrency: String,
    val targetCurrency: String,
    val currentCurrencyValue: String,
    val exchangeRate: String
) {



    fun getCurrentConvertedValue():String{
        val cur1 = currentCurrencyValue.toDoubleOrNull() ?: 0.0
        val cur2 = exchangeRate.toDoubleOrNull() ?: 0.0
        return (cur1*cur2).roundToOneDecimalPlace().toString()
    }

    fun getCurrentRateToTargetCurrency(): String {
        val cur2 = exchangeRate.toDoubleOrNull() ?: 0.0
        return "1 $currentCurrency = ${cur2.roundToOneDecimalPlace()} $targetCurrency"
    }


    fun getTargetRateToCurrentRateCurrency(): String {
        val cur1 = currentCurrencyValue.toDoubleOrNull() ?: 0.0
        val cur2 = exchangeRate.toDoubleOrNull() ?: 0.0
        return "1 $targetCurrency = ${(1.0/cur2).roundToOneDecimalPlace()} $currentCurrency"
    }




    companion object {
        fun getSampleObjectList() = buildList<CurrencyUiModel>() {
            for (i in 0..10)
                add(CurrencyUiModel("Aur$i", "$i", "$i", ""))
        }
    }
}