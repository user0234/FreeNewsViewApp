package com.example.assignmentfor8k.retrofit.userIp.Retrofit

import com.example.assignmentfor8k.util.Constants.BASE_URL_IP_COUNTRY
import com.example.assignmentfor8k.retrofit.userIp.api.IpRegionCallInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceIpCall {
    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL_IP_COUNTRY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val ipApi by lazy {
            retrofit.create(IpRegionCallInterface::class.java)
        }
    }
}