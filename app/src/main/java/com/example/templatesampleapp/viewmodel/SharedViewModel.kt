package com.example.templatesampleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.templatesampleapp.model.network.CurrencyRateJsonModel
import com.example.templatesampleapp.repository.ExchangeRateRepo
import com.example.templatesampleapp.utils.Either
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 11/9/2023 at 1:33 AM
 */

@HiltViewModel
class SharedViewModel @Inject constructor(private val exchangeRateRepo: ExchangeRateRepo) : ViewModel() {

     val _activeCurrencyValue = MutableLiveData<String>("0")
    val activeCurrencyValue
        get() =_activeCurrencyValue.value?:"0"


    private val _activeCurrencyName = MutableLiveData<String>("EUR")
    val activeCurrencyName
        get() =_activeCurrencyName.value?:"EUR"


    private var _currencyRatesData = MutableStateFlow<MainUiEvents>(MainUiEvents.Idle)
    var currencyRatesData = _currencyRatesData.asStateFlow()


     var _progressEvents = Channel<ProgressEvents>()





    suspend fun getExchangeRates(apiKey:String,currency:String){
        _progressEvents.send(ProgressEvents.ShowProgressDialog)
        // Add Force Dealy
        delay(1000*3)

        exchangeRateRepo.getExchangeRates(apiKey, currency).collect {
          it.onSuccess {
              _progressEvents.send(ProgressEvents.HideProgressDialog)
              _currencyRatesData.emit(MainUiEvents.GotData(it))
          }.onFailure {
              _progressEvents.send(ProgressEvents.HideProgressDialog)
              _currencyRatesData.emit(MainUiEvents.Error(it))
          }
        }
    }


}



sealed class MainUiEvents {

    data class Error(val msg: String) : MainUiEvents()
    data class GotData(val data: CurrencyRateJsonModel) : MainUiEvents()
    object Idle : MainUiEvents()
}
sealed class ProgressEvents {

    object ShowProgressDialog : ProgressEvents()
    object HideProgressDialog : ProgressEvents()
}

