package com.example.assignmentfor8k.retrofit.newsApi.model


import com.google.gson.annotations.SerializedName

data class TopNewsResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,

)