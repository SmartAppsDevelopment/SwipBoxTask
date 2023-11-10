package com.example.templatesampleapp.repository.network

import com.example.templatesampleapp.model.network.CurrencyRateJsonModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Umer Bilal
 * Created 11/10/2023 at 9:50 AM
 */
interface CurrencyExchangeService {


    @GET("{key}/latest/{currency}")
    fun getExchangeRates(
        @Path(value = "key") apiKey: String,
        @Path("currency") curency: String
    ): Call<CurrencyRateJsonModel>


}