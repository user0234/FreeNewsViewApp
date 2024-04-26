package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentTopNewsBinding
import com.example.assignmentfor8k.ui.activity.homeActivity.MainActivity
import com.example.assignmentfor8k.ui.activity.homeActivity.adaptors.ArticleListAdaptor
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.util.HelperFunction.showSnackBar
import com.example.assignmentfor8k.util.Resource
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsHeadLineFragment : Fragment(R.layout.fragment_top_news) {

    private lateinit var binding: FragmentTopNewsBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adaptor:ArticleListAdaptor
    private lateinit var adaptorSearch:ArticleListAdaptor
    private  var category:String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTopNewsBinding.bind(view)
        setUpViewModel()
        setUpCategoryChips()
        setUpNews()
        setUpSearchAdaptor()

        viewModel.getTopNews(null,"relevancy",0)

    }

    private fun setUpSearchAdaptor() {
        adaptorSearch = ArticleListAdaptor()
        binding.searchRecyclerView.adapter = adaptorSearch
        binding.searchRecyclerView.layoutManager  = LinearLayoutManager(activity)
        //  binding.newsListRv.addOnScrollListener(this@NewsHeadLineFragment.scrollListener)
        adaptorSearch.setOnItemClickListener { articleItem ->

        }

        var job: Job?= null
        binding.searchView.editText.addTextChangedListener  { text ->

            Log.i("searchNews","search query - $text")

            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                if (text != null) {
                    if(text.toString().isNotEmpty()) {
                        Log.i("searchNews","search news started")
                        viewModel.getSearchNews(text.toString(),"relevancy",category)
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    //   hideProgressBar(paginationProgressBar)
                    if (response.data != null) {
                        adaptorSearch.differ.submitList(response.data.articles.toList())
//                        val totalPages = response.data.totalResults / Constants.QUERY_PAGE_SIZE + 2
//                        isLastPage = viewModel.searchNewsPage == totalPages
//                        if (isLastPage) {
//                            rvSearchNews.setPadding(0, 0, 0, 0)
//                        }
                    }
                }

                is Resource.Error -> {
                    //   hideProgressBar(paginationProgressBar)
                    if (response.message != null) {
                        Log.e("newsSearchFragment", "error: ${response.message}")
                        showSnackBar("${response.message}", binding.searchView)
                    }
                }

                is Resource.Loading -> {
                    //    showProgressBar(paginationProgressBar)
                    showSnackBar("Loading",binding.searchView)
                }
            }
        }


    }


    private fun setUpNews() {
        adaptor = ArticleListAdaptor()
        binding.newsListRv.adapter = adaptor
        binding.newsListRv.layoutManager  = LinearLayoutManager(activity)
      //  binding.newsListRv.addOnScrollListener(this@NewsHeadLineFragment.scrollListener)
        adaptor.setOnItemClickListener { articleItem ->

        }

        viewModel.topNews.observe(viewLifecycleOwner) {response ->
            when(response){
                is Resource.Success -> {
               //     hideProgressBar(paginationProgressBar)
                    if (response.data != null) {
                        adaptor.differ.submitList(response.data.articles.toList())
//                        val totalPages = response.data.totalResults / QUERY_PAGE_SIZE + 2
//                        isLastPage = viewModel.breakingNewsPage == totalPages
//                        if(isLastPage) {
//                            rvBreakingNews.setPadding(0, 0, 0, 0)
//                        }
                    }
                }
                is Resource.Error -> {
                 //   hideProgressBar(paginationProgressBar)
                    if (response.message != null) {
                        Log.e(TAG,"error: ${response.message}")
                        showSnackBar("${response.message}",binding.root.rootView)
                    }
                }
                is Resource.Loading -> {
                    showSnackBar("Loading",binding.root.rootView)
                }
            }
        }


    }


    private fun hideProgressBar(paginationProgressBar: ProgressBar) {
        paginationProgressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar(paginationProgressBar: ProgressBar) {
        paginationProgressBar.visibility = View.VISIBLE

    }

    private fun setUpCategoryChips() {

        viewModel.getAllEnabledChips().observe(viewLifecycleOwner) {
            it?.forEach { chipItem ->
                val chip = Chip(requireContext())
                val text = chipItem.value
                chip.text = text
                chip.id = chipItem.id
                chip.isCheckable = true
                binding.categoryChipGroup.addView(chip)
            }
        }
        binding.categoryChipGroup.isSingleSelection = true
        binding.categoryChipGroup.isSelectionRequired = true
        binding.categoryChipGroup.check(0)

        binding.categoryChipGroup.setOnCheckedStateChangeListener { chipGroup, list ->
             Log.i("ChipGrp"," chipGrp - ${chipGroup} , list - ${list}")
            list.first()?.let {
                viewModel.startTopNewsFromCategoryId(it,"relevancy",0) { categoryName ->
                    category = categoryName
                }
            }

        }
    }

    private fun setUpViewModel() {
        viewModel = (activity as MainActivity).viewModel
    }

    companion object {
       private const val TAG = "HeadLineFragment"
    }


}