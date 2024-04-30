package com.example.assignmentfor8k.ui.activity.homeActivity.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.databinding.ItemArticlePreviewBinding
import com.example.assignmentfor8k.retrofit.newsApi.model.Article

class ArticleListAdaptor: RecyclerView.Adapter<ArticleListAdaptor.ArticleViewHolder>() {

     inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root)


    // for faster change in view
    private val differCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem && oldItem.url == newItem.url && oldItem.title == newItem.title
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
          val currentItem = differ.currentList.get(position)
          val binding = holder.binding

           binding.authorTv.text = currentItem.author
        binding.sourceTv.text = currentItem.source.name
        binding.titleTv.text  = currentItem.title

        Glide
            .with(binding.newsImageView)
            .load(currentItem.urlToImage)
            .placeholder(R.color.blue)
            .into(binding.newsImageView)

        binding.root.rootView.setOnClickListener {
            onItemClickListener?.let {
                it(currentItem)
            }
        }

    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }


}