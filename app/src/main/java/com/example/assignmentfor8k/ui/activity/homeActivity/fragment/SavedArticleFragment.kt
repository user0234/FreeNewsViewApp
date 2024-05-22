package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentSavedArticleBinding
import com.example.assignmentfor8k.ui.activity.homeActivity.MainActivity
import com.example.assignmentfor8k.ui.activity.homeActivity.adaptors.ArticleListAdaptor
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel


class SavedArticleFragment : Fragment(R.layout.fragment_saved_article) {


    private lateinit var binding:FragmentSavedArticleBinding
    private lateinit var adaptor: ArticleListAdaptor
    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.news_nav_graph_xml)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding  = FragmentSavedArticleBinding.bind(view)
         setUpViewModel()
        setUpListAdaptor()
        setUpSavedDataItem()
    }

    private fun setUpSavedDataItem() {
           viewModel.getSavedArticles().observe(viewLifecycleOwner) {
                if(it.isNullOrEmpty()) {
                    binding.newsListRv.visibility = View.GONE
                    binding.savedListEmpty.visibility= View.VISIBLE
                }else{
                    binding.newsListRv.visibility = View.VISIBLE
                    binding.savedListEmpty.visibility= View.GONE

                    adaptor.differ.submitList(it)
                }
           }
    }

    private fun setUpListAdaptor() {
        adaptor =  ArticleListAdaptor()
        binding.newsListRv.adapter = adaptor
        binding.newsListRv.layoutManager  = LinearLayoutManager(activity)
        adaptor.setOnItemClickListener { articleItem ->

            val bundle = Bundle().apply {
                putParcelable(ArticleViewFragment.ARTICLE_ARG,articleItem)
            }

            findNavController().navigate(
                R.id.action_savedArticleFragment_to_articleViewFragment2,bundle
            )
        }

        adaptor.setOnShare {
              viewModel.shareUrl(it)
        }
    }

    private fun setUpViewModel() {
     }



}