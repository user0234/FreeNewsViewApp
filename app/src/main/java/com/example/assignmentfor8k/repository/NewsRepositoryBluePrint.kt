package com.example.assignmentfor8k.repository

import androidx.lifecycle.LiveData
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.retrofit.newsApi.model.SearchNewsItem
import com.example.assignmentfor8k.retrofit.newsApi.model.TopNewsResponse
import retrofit2.Response


/**
 * blue print class for the repository to keep it clean
 */


interface NewsRepositoryBluePrint {

    suspend fun searchNewsArticles(
        searchQuery: String, sortBy: String,
        currentPageSize: Int
    ): Response<SearchNewsItem>

    suspend fun topNewArticles(
        sortBy: String,
        countryCode: String,
        currentPage: Int
    ): Response<TopNewsResponse>

    suspend fun topNewArticlesCategory(
        category: String,
        sortBy: String,
        countryCode: String,
        currentPage: Int
    ): Response<TopNewsResponse>

    suspend fun saveNewsArticle(article: Article)

    suspend fun deleteNewsArticle(article: Article)

    suspend fun deleteMultipleNewsArticle(articleList : List<Article>)

     fun getSavedArticlesLiveData(): LiveData<List<Article>?>
}