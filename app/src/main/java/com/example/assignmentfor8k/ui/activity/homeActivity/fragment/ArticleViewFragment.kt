package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.diebietse.webpage.downloader.DefaultFileSaver
import com.diebietse.webpage.downloader.WebpageDownloader
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentArticleViewFragmentBinding
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
import com.example.assignmentfor8k.ui.activity.homeActivity.MainActivity
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.util.HelperFunction.showSnackBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.absoluteValue

class ArticleViewFragment : Fragment(R.layout.fragment_article_view_fragment) {

    private lateinit var binding: FragmentArticleViewFragmentBinding
    private lateinit var viewModel: MainViewModel
    private var article:Article? = null
     val args: ArticleViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleViewFragmentBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        article =  args.article

        val webView: WebView = binding.webView

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article!!.url)
            settings.allowContentAccess = true
            settings.allowFileAccess = true
        }

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            showProgressBar()
            showSnackBar("Saving article", it)
            val downloadDir = File(requireContext().applicationContext.filesDir, "offline")
            val pageId = article.hashCode().absoluteValue.toString()

            lifecycleScope.launch(Dispatchers.IO) {
               try{

                   val url = "file:/${downloadDir}/${pageId}/index.html"
                   WebpageDownloader().download(
                       article!!.url,
                       DefaultFileSaver(File(downloadDir, pageId))
                   )
                   article!!.url = url
                   viewModel.saveArticle(article!!)
                   showSnackBar("article Saved", it)
                   withContext(Dispatchers.Main) {
                       hideProgressBar()
                   }
               } catch (e:Exception) {
                   withContext(Dispatchers.Main) {
                       hideProgressBar()
                   }
                   showSnackBar("Some error occurred while saving article ", it)
               }
            }

        }

    }

    private fun showProgressBar() {
        binding.saveArticleProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.saveArticleProgressBar.visibility = View.GONE
    }



    companion object {

        val ARTICLE_ARG = "article"

        @JvmStatic
        fun newInstance(article: Article) =
            ArticleViewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARTICLE_ARG, article)

                }
            }
    }

}