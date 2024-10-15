package com.example.assignmentfor8k.ui.activity.homeActivity.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.diebietse.webpage.downloader.DefaultFileSaver
import com.diebietse.webpage.downloader.WebpageDownloader
import com.example.assignmentfor8k.applicationClass.NewsApplication
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass
import com.example.assignmentfor8k.repository.ChipRepository
import com.example.assignmentfor8k.repository.NewsRepository
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.retrofit.newsApi.model.SearchNewsItem
import com.example.assignmentfor8k.retrofit.newsApi.model.TopNewsResponse
import com.example.assignmentfor8k.retrofit.userIp.Retrofit.RetrofitInstanceIpCall
import com.example.assignmentfor8k.retrofit.userIp.api.IpRegionCallInterface
import com.example.assignmentfor8k.util.Event
import com.example.assignmentfor8k.util.Resource
import com.example.assignmentfor8k.util.send
import com.example.assignmentfor8k.util.sharePref.IpBasedRegion.getCountry
import com.example.assignmentfor8k.util.sharePref.IpBasedRegion.updateCountry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File
import javax.inject.Inject
import kotlin.math.absoluteValue

/**
 * Main View Model to save state of the fragment and communication between activity and fragments
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val newsRepository: NewsRepository,
    private val chipRepository: ChipRepository,
    private val ipApi: IpRegionCallInterface
) : AndroidViewModel(app) {

    /**
     * article database management functions
     */
    fun saveArticle(
        article: Article, applicationContext: Context, callback: (String, Boolean) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            if (article.url.startsWith("file://")) {
                newsRepository.deleteNewsArticle(article)
                callback("article removed", false)

            } else {
                callback("Saving article", true)

                val downloadDir = File(applicationContext.filesDir, "offline")
                val pageId = article.hashCode().absoluteValue.toString()

                try {

                    val url = "file://${downloadDir}/${pageId}/index.html"
                    WebpageDownloader().download(
                        article.url, DefaultFileSaver(File(downloadDir, pageId))
                    )
                    article.url = url
                    newsRepository.saveNewsArticle(article)
                    callback("article Saved", false)

                } catch (e: Exception) {
                    callback("Some error occurred while saving article", false)

                }

            }

        }

    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.deleteNewsArticle(article)
        }

    }


    fun getSavedArticles() = newsRepository.getSavedArticlesLiveData()


    /**
     * category database functions
     */
    fun saveChip(chipItem: ChipDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            chipRepository.enableChip(chipItem)
        }
    }

    fun getAllEnabledChips(): LiveData<List<ChipDataClass>?> {
        return chipRepository.getAllEnabledChips()
    }

    fun getAllChips(): LiveData<List<ChipDataClass>?> {
        return chipRepository.getAllChips()
    }


    /**
     * Top News setup
     */
    var topNewsPages: Int = 1

    val topNews: MutableLiveData<Resource<TopNewsResponse>> = MutableLiveData()

    private var topNewsResponse: TopNewsResponse? = null

    /**
     * func to trigger news function in io coroutine
     */
    fun getTopNews(category: String?, sortedBy: String, currentPage: Int?) = viewModelScope.launch {
        Log.i(
            "getNews",
            " get News category - $category, currentpage - $currentPage , topnewspage - $topNewsPages"
        )

        if (currentPage != null) {
            topNewsPages = currentPage
        }
        val countryCode = getCountry(getApplication())
        safeBreakingNews(category, sortedBy, topNewsPages, countryCode.lowercase())

    }

    /**
     * func to handle the news response and handling errors
     */
    private suspend fun safeBreakingNews(
        category: String?,
        sortedBy: String,
        pageItem: Int,
        countryCode: String
    ) {
        topNews.postValue(Resource.Loading())
        try {

            if (hasInternetConnection()) {
                val response = if (category == null) {
                    newsRepository.topNewArticles(sortedBy, countryCode, pageItem)
                } else {
                    newsRepository.topNewArticlesCategory(category, sortedBy, countryCode, pageItem)
                }

                topNews.postValue(handleTopNewsResponse(response))
            } else {
                topNews.postValue(Resource.Error("Please Check Internet Connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            topNews.postValue(Resource.Error("an Error has occurred ${e.message}"))
        }
    }


    /**
     * func to handle the top news response and updating progress and results with live data
     */

    private fun handleTopNewsResponse(response: Response<TopNewsResponse>): Resource<TopNewsResponse> {
        if (response.isSuccessful) {
            if (response.body() != null) {
                if (topNewsResponse == null) {
                    topNewsResponse = response.body()
                } else {
                    Log.i(
                        "getNews",
                        " response item size  handle- ${topNewsResponse?.articles?.size} , "
                    )

                    if (topNewsPages == 1) {
                        topNewsResponse = response.body()
                    } else {
                        val newArticles = response.body()!!.articles
                        topNewsResponse!!.articles.addAll(newArticles)
                    }

                }
                Log.i(
                    "getNews", " response item size  handle- ${topNewsResponse?.articles?.size} , "
                )
                topNewsPages++
                return Resource.Success(topNewsResponse ?: response.body())
            }


        }

        return Resource.Error(response.message())
    }

    /**
     * func to start top news response when category are switches
     */
    fun startTopNewsFromCategoryId(
        first: Int, sortedBy: String, currentPage: Int, callback: (String?) -> Unit
    ) {
        topNewsPages = 1
        viewModelScope.launch {
            val getItemWithId: ChipDataClass? = chipRepository.getItemFromId(first)
            getTopNews(getItemWithId?.value, sortedBy, currentPage)
            callback(getItemWithId?.value)
        }

    }


    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    /**
     * search for news func similar to the top news with query peremeter
     */
    val searchNews: MutableLiveData<Resource<SearchNewsItem>> = MutableLiveData()

    private var searchNewsResponse: SearchNewsItem? = null
    fun getSearchNews(query: String, sortedBy: String, category: String?) = viewModelScope.launch {
        safeSearchNews(query, sortedBy)
    }

    private suspend fun safeSearchNews(query: String, sortedBy: String) {
        Log.i("searchNews", "search news started")

        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchNewsArticles(query, sortedBy, 20)

                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("Check your internet"))
            }

        } catch (t: Throwable) {
            searchNews.postValue(Resource.Error("an Error has occurred"))
        }
    }

    private fun handleSearchNewsResponse(response: Response<SearchNewsItem>): Resource<SearchNewsItem> {
        if (response.isSuccessful) {
            if (response.body() != null) {
                //  searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = response.body()
                }
                return Resource.Success(searchNewsResponse ?: response.body())
            }
        }

        return Resource.Error(response.message())
    }

    /**
     * Event handler to share url
     */

    private val _shareTextEvent = MutableLiveData<Event<String>>()
    val handleShareText: LiveData<Event<String>>
        get() = _shareTextEvent

    fun shareUrl(it: String) {
        _shareTextEvent.send(it)
    }

    fun startIpGetter() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = ipApi.getIPInfo()

                if (response.isSuccessful) {
                    Log.i("ipValue", "ip call resposne, - ${response.body()}")

                    updateCountry(
                        getApplication<Application>().applicationContext,
                        response.body()?.country ?: "IN"
                    )

                } else {
                    Log.i("ipValue", "ip call error, - ${response.errorBody()}")

                }
            } catch (exp: Exception) {
                //  exp.printStackTrace()
                //  Log.i("ipValue", "ip call error, - ${exp.code()}")
                Log.i("ipValue", "ip call stack, - ${exp.printStackTrace()}")

            }

        }

    }

}