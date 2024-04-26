package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentNewsSearchBinding
import com.example.assignmentfor8k.ui.activity.homeActivity.MainActivity
import com.example.assignmentfor8k.ui.activity.homeActivity.adaptors.ArticleListAdaptor
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.util.HelperFunction.showSnackBar
import com.example.assignmentfor8k.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsSearchFragment : Fragment(R.layout.fragment_news_search) {

    private lateinit var binding: FragmentNewsSearchBinding
    private lateinit var adaptor:ArticleListAdaptor
    private lateinit var viewModel: MainViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsSearchBinding.bind(view)
        setUpViewModel()


    }

    private fun setUpViewModel() {
        viewModel = (activity as MainActivity).viewModel
    }

}