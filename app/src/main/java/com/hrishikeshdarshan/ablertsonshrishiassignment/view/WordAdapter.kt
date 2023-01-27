package com.hrishikeshdarshan.albertsonsassignment.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail
import com.hrishikeshdarshan.ablertsonshrishiassignment.databinding.ActivityHomeItemBinding
import com.hrishikeshdarshan.ablertsonshrishiassignment.util.MyDiffUtil

class WordAdapter(private var wordList: List<WordDetail> = emptyList()) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ActivityHomeItemBinding.inflate(LayoutInflater.from(parent.context))
        return WordViewHolder(binding)
    }

    fun update(newWordList: List<WordDetail>) {
        val diffUtil = MyDiffUtil(wordList, newWordList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        wordList = newWordList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) =
        holder.bind(wordList[position])

    override fun getItemCount(): Int = wordList.size

    class WordViewHolder(private val binding: ActivityHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: WordDetail) {
            binding.textLongForm.text = word.longForm
            binding.textFrequency.text = word.frequency.toString()
            binding.textSince.text = word.since.toString()
        }
    }
}