package com.example.templatesampleapp.repository

import com.example.templatesampleapp.model.network.CurrencyRateJsonModel
import com.example.templatesampleapp.utils.Either
import kotlinx.coroutines.flow.Flow

/**
 * @author Umer Bilal
 * Created 11/10/2023 at 9:48 AM
 */
interface ExchangeRateRepo {

    suspend fun getExchangeRates(apiKey:String,currency:String): Flow<Either<CurrencyRateJsonModel>>

}