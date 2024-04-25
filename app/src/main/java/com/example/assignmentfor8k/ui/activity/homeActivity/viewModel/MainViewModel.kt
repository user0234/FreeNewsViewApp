package com.example.assignmentfor8k.ui.activity.homeActivity.viewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.assignmentfor8k.applicationClass.AppApplicationClass
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass
import com.example.assignmentfor8k.repository.ChipRepository
import com.example.assignmentfor8k.repository.NewsRepository
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.retrofit.newsApi.model.TopNewsResponse
import com.example.assignmentfor8k.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val app: AppApplicationClass,
    private val newsRepository: NewsRepository,
    private val chipRepository: ChipRepository
) : AndroidViewModel(app) {

    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.saveNewsArticle(article)
        }

    }

    fun saveChip(chipItem: ChipDataClass) {
        viewModelScope.launch(Dispatchers.IO) {
            chipRepository.enableChip(chipItem)
        }
    }

    fun getAllEnabledChips(): LiveData<List<ChipDataClass>?> {
        return chipRepository.getAllEnabledChips()
    }

    /**
     * Top News setup
     */

    val topNews: MutableLiveData<Resource<TopNewsResponse>> = MutableLiveData()

    var topNewsResponse: TopNewsResponse? = null

    fun getTopNews(category: String?, sortedBy: String, pageItem: Int) = viewModelScope.launch {
        safeBreakingNews(category, sortedBy, pageItem)
    }

    private suspend fun safeBreakingNews(category: String?, sortedBy: String, pageItem: Int) {
        topNews.postValue(Resource.Loading())
        try {
            if (true) {
                val response = if (category == null) {
                    newsRepository.topNewArticles(sortedBy, "us", pageItem)
                } else {
                    newsRepository.topNewArticlesCategory(category, sortedBy, "us", pageItem)
                }

                topNews.postValue(handleBreakingNewsResponse(response))
            } else {
                topNews.postValue(Resource.Error("Please Check Internet Connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("MainViewModel"," error message - ${e.message}")
            topNews.postValue(Resource.Error("an Error has occurred ${e.message}"))
        }
    }

    private fun handleBreakingNewsResponse(response: Response<TopNewsResponse>): Resource<TopNewsResponse> {
        if (response.isSuccessful) {
            if (response.body() != null) {

//                if(breakingNewsResponse == null){
//
//                }else{
//                    val oldArticles = breakingNewsResponse!!.articles
//                    val newArticles = response.body()!!.articles
//                    oldArticles.addAll(newArticles)
//
//                }
                topNewsResponse = response.body()
                return Resource.Success(topNewsResponse ?: response.body())
            }
        }

        return Resource.Error(response.message())
    }


    private fun hasInternetConnection():Boolean {
        val connectivityManager =  getApplication<AppApplicationClass>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =  connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun startTopNewsFromCategoryId(first: Int,sortedBy:String,currentPage :Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val getItemWithId:ChipDataClass? = chipRepository.getItemFromId(first)
            getTopNews(getItemWithId?.value,sortedBy,currentPage)
        }

    }


}