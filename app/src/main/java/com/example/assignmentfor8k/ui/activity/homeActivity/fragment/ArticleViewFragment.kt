package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentArticleViewFragmentBinding
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleViewFragment : Fragment(R.layout.fragment_article_view_fragment) {

    private lateinit var binding: FragmentArticleViewFragmentBinding
    lateinit var viewModel: MainViewModel

    // todo
   // val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleViewFragmentBinding.bind(view)

     //   val article: Article = args.article
        val webView: WebView = view.findViewById(R.id.webView)
        webView.apply {
            webViewClient = WebViewClient()
         //   loadUrl(article.url)
        }

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
          //  viewModel.saveArticle(article)
            Snackbar.make(view, "saved in storage", Snackbar.LENGTH_SHORT).show()
        }

    }

}