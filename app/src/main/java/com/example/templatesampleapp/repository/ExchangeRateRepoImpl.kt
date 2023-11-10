package com.example.templatesampleapp.repository

import com.example.templatesampleapp.model.network.CurrencyRateJsonModel
import com.example.templatesampleapp.repository.network.CurrencyExchangeService
import com.example.templatesampleapp.utils.Either
import com.example.templatesampleapp.utils.showLog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 11/10/2023 at 9:48 AM
 */


class ExchangeRateRepoImpl @Inject constructor(
    private val currencyExchangeService: CurrencyExchangeService
) : ExchangeRateRepo {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getExchangeRates(
        apiKey: String,
        currency: String
    ): Flow<Either<CurrencyRateJsonModel>> {
        return callbackFlow {

            currencyExchangeService.getExchangeRates(apiKey, currency)
                .enqueue(object : Callback<CurrencyRateJsonModel> {
                    override fun onResponse(
                        call: Call<CurrencyRateJsonModel>,
                        response: Response<CurrencyRateJsonModel>
                    ) {
                        if (response.isSuccessful) {
                            trySendBlocking(Either.Success(response.body()!!))
                        } else {
                            trySendBlocking(Either.Error("Code ${response.code()} - Msg ${response.message()} "))
                        }
                    }

                    override fun onFailure(call: Call<CurrencyRateJsonModel>, t: Throwable) {
                        showLog(t.message + "", "RetrofApiLog")
                        trySendBlocking(Either.Error("Msg = ${t.message}  "))
                    }
                })
            awaitClose()
        }
    }
}