package com.example.templatesampleapp.di.hilt


import android.content.Context
import com.example.templatesampleapp.helper.Constants.BASE_URL
import com.example.templatesampleapp.model.network.CurrencyRateJsonModel
import com.example.templatesampleapp.repository.ExchangeRateRepo
import com.example.templatesampleapp.repository.ExchangeRateRepoImpl
import com.example.templatesampleapp.repository.network.CurrencyExchangeService
import com.example.templatesampleapp.utils.getChuckerInterCeptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @HttpLoggerInterceptorBasic
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }


    @HttpLoggerInterceptorBody
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor1(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


    @HttpLoggerInterceptorHeader
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor2(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }


    @Singleton
    @Provides
    fun providesOkHttpClient(@ApplicationContext context: Context, @HttpLoggerInterceptorBody httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(getChuckerInterCeptor(context))
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): CurrencyExchangeService = retrofit.create(CurrencyExchangeService::class.java)

    @Singleton
    @Provides
    fun getRepository( currencyExchangeService: CurrencyExchangeService) : ExchangeRateRepo= ExchangeRateRepoImpl(currencyExchangeService)
}