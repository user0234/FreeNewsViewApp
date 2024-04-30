package com.example.assignmentfor8k.ui.activity.homeActivity.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentfor8k.database.chipsDataBase.CategoryChipStatus
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass
import com.example.assignmentfor8k.databinding.ItemArticlePreviewBinding
import com.example.assignmentfor8k.databinding.ItemChipEnableLayoutBinding
import com.example.assignmentfor8k.retrofit.newsApi.model.Article


class ChipItemEnableListAdaptor: RecyclerView.Adapter<ChipItemEnableListAdaptor.ChipItemViewHolder>() {

    inner class ChipItemViewHolder(val binding: ItemChipEnableLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ChipDataClass>(){
        override fun areItemsTheSame(oldItem: ChipDataClass, newItem: ChipDataClass): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChipDataClass, newItem: ChipDataClass): Boolean {
            return oldItem == newItem && oldItem.id == newItem.id && oldItem.status == newItem.status
        }

    }

    val differ = AsyncListDiffer(this,differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipItemViewHolder {
         return ChipItemViewHolder(
             ItemChipEnableLayoutBinding.inflate(
                 LayoutInflater.from(parent.context),
                 parent,
                 false
             )
         )
    }

    override fun getItemCount(): Int {
           return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ChipItemViewHolder, position: Int) {
        val currentItem = differ.currentList.get(position)
        val binding = holder.binding

        binding.textView.text = currentItem.value
        binding.checkbox.isChecked = currentItem.status == CategoryChipStatus.Enabled

        binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            onItemClickListener?.let {
                currentItem.status = if(isChecked) {
                    CategoryChipStatus.Enabled
                }else {
                    CategoryChipStatus.Disabled
                }
                it(currentItem)
            }
        }


    }

    private var onItemClickListener: ((ChipDataClass) -> Unit)? = null

    fun setOnItemClickListener(listener: (ChipDataClass) -> Unit) {
        onItemClickListener = listener
    }

}