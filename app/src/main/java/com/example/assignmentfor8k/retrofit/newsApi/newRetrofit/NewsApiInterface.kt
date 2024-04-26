package com.example.assignmentfor8k.retrofit.newsApi.newRetrofit

import com.example.assignmentfor8k.retrofit.newsApi.model.SearchNewsItem
import com.example.assignmentfor8k.retrofit.newsApi.model.TopNewsResponse
import com.example.assignmentfor8k.util.Constants.NEWS_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    @GET("top-headlines")
    suspend fun getTopNewsAll(
        @Query("country") countryCode: String,
        @Query("pageSize") totalPageSize: Int,
        @Query("page") currentPageSize: Int,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") newsApi: String = NEWS_API_KEY,
    ): Response<TopNewsResponse>

    @GET("top-headlines")
    suspend fun getTopNewsCategory(
        @Query("country") countryCode: String,
        @Query("category") category: String?,
        @Query("pageSize") totalPageSize: Int,
        @Query("page") currentPageSize: Int,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") newsApi: String = NEWS_API_KEY,
    ): Response<TopNewsResponse>

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") newsApi: String = NEWS_API_KEY,
    ): Response<SearchNewsItem>


}