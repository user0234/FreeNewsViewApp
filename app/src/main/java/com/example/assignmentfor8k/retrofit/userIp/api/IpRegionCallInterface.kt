package com.example.assignmentfor8k.retrofit.userIp.api

import com.example.assignmentfor8k.retrofit.userIp.model.IpDataItem
import retrofit2.Response
import retrofit2.http.GET

interface IpRegionCallInterface {

    @GET("/")
     suspend fun getIPInfo(): Response<IpDataItem>
}