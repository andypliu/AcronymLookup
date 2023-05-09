package com.albertsons.acronyms.network.service

import com.albertsons.acronyms.network.service.ServiceConstant.TIMEOUT
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceProvider {

    fun getAcronymService() : AcronymService {

        // Setup client
        val httpBuilder = OkHttpClient.Builder()
        val client = httpBuilder
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)      // Setup timeout for network call
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        // Setup Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(ServiceConstant.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // return the REST API service
        return retrofit.create(AcronymService::class.java)
    }
}