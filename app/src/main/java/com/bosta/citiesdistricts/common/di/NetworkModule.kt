package com.bosta.citiesdistricts.common.di

import android.util.Log
import com.bosta.citiesdistricts.BuildConfig
import com.bosta.citiesdistricts.common.data.repository.remote.BostaApiService
import com.bosta.citiesdistricts.common.data.repository.remote.RetrofitRestApiNetworkProvider
import com.bosta.citiesdistricts.common.domain.repository.remote.IRestApiNetworkProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val READ_TIMEOUT = 30L
        private const val WRITE_TIMEOUT = 30L
        private const val CONNECT_TIMEOUT = 15L
        private const val MAX_IDLE_CONNECTIONS = 10
        private const val KEEP_ALIVE_DURATION = 10L
        private const val LOG_TAG = "Network"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRestApiNetworkProvider(apiService: BostaApiService): IRestApiNetworkProvider =
        RetrofitRestApiNetworkProvider(apiService)

    @Provides
    @Singleton
    fun provideBostaApiService(retrofit: Retrofit): BostaApiService =
        retrofit.create(BostaApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .connectionPool(
            ConnectionPool(
                MAX_IDLE_CONNECTIONS,
                KEEP_ALIVE_DURATION,
                TimeUnit.MINUTES
            )
        )
        .build()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Log.d(LOG_TAG, message)
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
}