package com.example.assignmentfor8k.repository

import androidx.lifecycle.LiveData
import com.example.assignmentfor8k.database.newsDataBase.NewsDao
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.retrofit.newsApi.model.SearchNewsItem
import com.example.assignmentfor8k.retrofit.newsApi.model.TopNewsResponse
import com.example.assignmentfor8k.retrofit.newsApi.newRetrofit.NewsApiInterface
import com.example.assignmentfor8k.retrofit.newsApi.newRetrofit.NewsRetrofitInstance
import com.example.assignmentfor8k.util.Constants.QUERY_PAGE_SIZE
import com.example.assignmentfor8k.util.HelperFunction.getCurrentDate
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApi: NewsApiInterface
):
    NewsRepositoryBluePrint {

    override suspend fun searchNewsArticles(
        searchQuery: String,
        sortBy: String,
        currentPageSize: Int
    ): Response<SearchNewsItem> {
        val getCurrentDate: String = getCurrentDate()

        return newsApi.getSearchNews(
            query = searchQuery,
            sortBy = sortBy,
        )
    }

    override suspend fun topNewArticles(
        sortBy: String,
        countryCode: String,
        currentPage: Int
    ): Response<TopNewsResponse> {

        return newsApi.getTopNewsAll(
            countryCode,
            totalPageSize = QUERY_PAGE_SIZE,
            currentPageSize = currentPage
        )
    }

    override suspend fun topNewArticlesCategory(
        category: String,
        sortBy: String,
        countryCode: String,
        currentPage: Int
    ): Response<TopNewsResponse> {

        return newsApi.getTopNewsCategory(

            countryCode,
            category,
            totalPageSize = QUERY_PAGE_SIZE,
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

    override fun getSavedArticlesLiveData(): LiveData<List<Article>?> {
        return newsDao.getSavedNewsArticles()
    }
}