package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentNewsSearchBinding


class NewsSearchFragment : Fragment(R.layout.fragment_news_search) {

    private lateinit var binding: FragmentNewsSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsSearchBinding.bind(view)

    }
}