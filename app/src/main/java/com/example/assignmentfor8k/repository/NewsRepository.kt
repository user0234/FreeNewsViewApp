package com.example.assignmentfor8k.repository

import androidx.lifecycle.LiveData
import com.example.assignmentfor8k.database.newsDataBase.NewsDao
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.retrofit.newsApi.model.SearchNewsItem
import com.example.assignmentfor8k.retrofit.newsApi.model.TopNewsResponse
import com.example.assignmentfor8k.retrofit.newsApi.newRetrofit.NewsRetrofitInstance
import com.example.assignmentfor8k.util.HelperFunction.getCurrentDate
import retrofit2.Response

class NewsRepository(private val newsDao: NewsDao) : NewsRepositoryBluePrint {


    override suspend fun searchNewsArticles(
        searchQuery: String,
        sortBy: String,
        currentPageSize: Int
    ): Response<SearchNewsItem> {
        val getCurrentDate: String = getCurrentDate()

        return NewsRetrofitInstance.newsApi.getSearchNews(
            query = searchQuery,
            fromDate = getCurrentDate,
            toDate = getCurrentDate,
            sortBy = sortBy,
            totalPageSize = 20,
            currentPageSize = currentPageSize
        )
    }

    override suspend fun topNewArticles(
        sortBy: String,
        countryCode: String,
        currentPage: Int
    ): Response<TopNewsResponse> {

        return NewsRetrofitInstance.newsApi.getTopNewsAll(
            countryCode,
            sortBy = sortBy,
            totalPageSize = 20,
            currentPageSize = currentPage
        )
    }

    override suspend fun topNewArticlesCategory(
        category: String,
        sortBy: String,
        countryCode: String,
        currentPage: Int
    ): Response<TopNewsResponse> {

        return NewsRetrofitInstance.newsApi.getTopNewsCategory(

            countryCode,
            category,
            sortBy = sortBy,
            totalPageSize = 20,
            currentPageSize = currentPage
        )
    }


    override suspend fun saveNewsArticle(article: Article) {
        newsDao.saveArticle(article)
    }

    override suspend fun deleteNewsArticle(article: Article) {
        newsDao.deleteArticle(article)
    }

    override suspend fun deleteMultipleNewsArticle(articleList: List<Article>) {
        //  newsDao.deleteAllArticle(articleList)
    }

    override suspend fun getSavedArticlesLiveData(): LiveData<List<Article>?> {
        return newsDao.getSavedNewsArticles()
    }
}