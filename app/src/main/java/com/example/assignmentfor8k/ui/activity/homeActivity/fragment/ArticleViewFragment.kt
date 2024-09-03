package com.example.assignmentfor8k.ui.activity.homeActivity.fragment

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.diebietse.webpage.downloader.DefaultFileSaver
import com.diebietse.webpage.downloader.WebpageDownloader
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.FragmentArticleViewFragmentBinding
import com.example.assignmentfor8k.retrofit.newsApi.model.Article
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
    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.news_nav_graph_xml)
    private var article: Article? = null
    val args: ArticleViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleViewFragmentBinding.bind(view)
        article = args.article


        val webView: WebView = binding.webView

        webView.apply {

            Log.i("ArticleFragment", "Error -  url -${article?.url}")

            webViewClient = WebViewClient().apply {
                object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        if (url != null) {
                            view?.loadUrl(url)
                            showProgressBar()
                        }
                        return true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        hideProgressBar()
                        super.onPageFinished(view, url)
                    }

                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler?,
                        error: SslError?
                    ) {
                        Log.i("ArticleFragment", "Error -  ssl -${error?.primaryError}")

                        super.onReceivedSslError(view, handler, error)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        Log.i("ArticleFragment", "Error -  webres -${error?.description}")
                        super.onReceivedError(view, request, error)
                    }

                    override fun onReceivedHttpError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        errorResponse: WebResourceResponse?
                    ) {
                        Log.i("ArticleFragment", "Error -  http -${errorResponse}")
                        super.onReceivedHttpError(view, request, errorResponse)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        errorCode: Int,
                        description: String?,
                        failingUrl: String?
                    ) {
                        Log.i("ArticleFragment", "Error -  failing -${description}")

                        super.onReceivedError(view, errorCode, description, failingUrl)
                    }
                }
            }
            val newUA =
                "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
            settings.userAgentString = newUA

            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            loadUrl(article!!.url)
        }

        // saving the article offline and then replacing the url for offline reading

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            showProgressBar()
            viewModel.saveArticle(article!!, requireContext()) { mes, loading ->
                lifecycleScope.launch(Dispatchers.Main) {
                    showSnackBar(mes, it)

                    if (!loading) {
                        hideProgressBar()
                    }

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

    }

}