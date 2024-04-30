package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentTopNewsBinding
import com.example.assignmentfor8k.ui.activity.homeActivity.MainActivity
import com.example.assignmentfor8k.ui.activity.homeActivity.adaptors.ArticleListAdaptor
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.util.Constants
import com.example.assignmentfor8k.util.Constants.QUERY_PAGE_SIZE
import com.example.assignmentfor8k.util.HelperFunction.showSnackBar
import com.example.assignmentfor8k.util.Resource
import com.google.android.material.chip.Chip
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsHeadLineFragment : Fragment(R.layout.fragment_top_news) {

    var isLoading = false
    var isLastPage = false
    var isScrolling = false
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

        viewModel.getTopNews(null,"relevancy",1)

    }

    private fun setUpSearchAdaptor() {
        adaptorSearch = ArticleListAdaptor()
        binding.searchRecyclerView.adapter = adaptorSearch
        binding.searchRecyclerView.layoutManager  = LinearLayoutManager(activity)
        //  binding.newsListRv.addOnScrollListener(this@NewsHeadLineFragment.scrollListener)
        adaptorSearch.setOnItemClickListener { articleItem ->

            val bundle = Bundle().apply {
                putParcelable(ArticleViewFragment.ARTICLE_ARG,articleItem)
            }

            findNavController().navigate(
                R.id.action_newsHeadLineFragment_to_articleViewFragment,bundle
            )

        }

        adaptorSearch.setOnShare {
            viewModel.shareUrl(it)
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
                       hideProgressBar(binding.paginationProgressBarSearch)
                    if (response.data != null) {
                        adaptorSearch.differ.submitList(response.data.articles.toList().filter { it.urlToImage!= null })
                    }
                }

                is Resource.Error -> {
                      hideProgressBar(binding.paginationProgressBarSearch)
                    if (response.message != null) {
                        Log.e("newsSearchFragment", "error: ${response.message}")
                        showSnackBar("${response.message}", binding.searchView)
                    }
                }

                is Resource.Loading -> {
                      showProgressBar(binding.paginationProgressBarSearch)
                }
            }
        }


    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true

            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount =  layoutManager.childCount
            val totalItemCount = adaptor.differ.currentList.size

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPos + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPos >= 0
            val isTotalMoreThenVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThenVisible && isScrolling

            Log.i("getNews","sholdPag - $shouldPaginate ")


            if(shouldPaginate)
            {   if(category=="All"){
                viewModel.getTopNews(null, sortedBy = "relevancy",null)
                isScrolling = false
            }else{
                viewModel.getTopNews(category, sortedBy = "relevancy",null)
                isScrolling = false
            }

            }

        }
    }


    private fun setUpNews() {
        adaptor = ArticleListAdaptor()
        binding.newsListRv.adapter = adaptor
        binding.newsListRv.layoutManager  = LinearLayoutManager(activity)
        binding.newsListRv.addOnScrollListener(this@NewsHeadLineFragment.scrollListener)
        adaptor.setOnItemClickListener { articleItem ->

            val bundle = Bundle().apply {
                putParcelable(ArticleViewFragment.ARTICLE_ARG,articleItem)
            }

            findNavController().navigate(
                R.id.action_newsHeadLineFragment_to_articleViewFragment,bundle
            )
        }

        adaptor.setOnShare {
            viewModel.shareUrl(it)
        }

        viewModel.topNews.observe(viewLifecycleOwner) {response ->

            when(response){
                is Resource.Success -> {
                   hideProgressBar(binding.paginationProgressBar)
                    if (response.data != null) {
                        Log.i("getNews"," response item size resp - ${response.data.articles.size} , ")

                        adaptor.differ.submitList(response.data.articles.toList())

                        val totalPages = response.data.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.topNewsPages == totalPages
                        if(isLastPage) {
                            binding.newsListRv.setPadding(0, 0, 0, 0)
                        }
                        Log.i("getNews"," isLastPage - $isLastPage ")

                    }
                }
                is Resource.Error -> {
                    hideProgressBar(binding.paginationProgressBar)
                    if (response.message != null) {
                        Log.e(TAG,"error: ${response.message}")
                        showSnackBar("${response.message}",binding.root.rootView)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar(binding.paginationProgressBar)
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
             Log.i("getNews","")


            list.first()?.let {

                if(it==0){
                    Log.i("getNews"," chip item - first")

                    viewModel.getTopNews(null,"relevancy",1)
                }else{
                    Log.i("getNews"," chip item - not all item")


                    viewModel.startTopNewsFromCategoryId(it,"relevancy",1) { categoryName ->
                        category = categoryName
                    }
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