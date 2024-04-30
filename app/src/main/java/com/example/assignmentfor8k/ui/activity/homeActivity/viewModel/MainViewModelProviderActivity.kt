package com.example.assignmentfor8k.ui.activity.homeActivity.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignmentfor8k.applicationClass.NewsApplication
import com.example.assignmentfor8k.repository.ChipRepository
import com.example.assignmentfor8k.repository.NewsRepository

class MainViewModelProviderActivity(
    private val app: Application,
    private val newsRepository: NewsRepository,
    private val  chipRepository: ChipRepository

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app,newsRepository, chipRepository) as T
    }

}