package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentProfileBinding
import com.example.assignmentfor8k.ui.activity.homeActivity.MainActivity
import com.example.assignmentfor8k.ui.activity.homeActivity.adaptors.ArticleListAdaptor
import com.example.assignmentfor8k.ui.activity.homeActivity.adaptors.ChipItemEnableListAdaptor
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.util.HelperFunction


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var chipListAdaptor:ChipItemEnableListAdaptor

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.news_nav_graph_xml)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        setUpViewModel()
        setUpListAdaptor()
        setUpData()
    }

    private fun setUpData() {
           viewModel.getAllChips().observe(viewLifecycleOwner) {
                chipListAdaptor.differ.submitList(it?.subList(1,it.size))
           }
    }

    private fun setUpListAdaptor() {
        chipListAdaptor = ChipItemEnableListAdaptor()

        binding.newsListRv.adapter = chipListAdaptor
        binding.newsListRv.layoutManager  = LinearLayoutManager(activity)
        //  binding.newsListRv.addOnScrollListener(this@NewsHeadLineFragment.scrollListener)
        chipListAdaptor.setOnItemClickListener { chipItem ->

            viewModel.saveChip(chipItem)
        }

    }

    private fun setUpViewModel() {

    }

}