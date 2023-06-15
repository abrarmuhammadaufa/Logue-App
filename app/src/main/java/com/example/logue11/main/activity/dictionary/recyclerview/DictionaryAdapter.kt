package com.example.logue11.main.activity.dictionary.recyclerview

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logue11.R

class DictionaryAdapter(private val wordList:ArrayList<Dictionary>) : RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {
    class DictionaryViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvWord = view.findViewById<TextView>(R.id.tv_word)
        val tvTran = view.findViewById<TextView>(R.id.tv_translation)
        val ivVoice = view.findViewById<ImageView>(R.id.iv_voice)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dictionary, parent, false)
        return DictionaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        val (word, translation, voice) = wordList[position]
        holder.tvWord.text = word
        holder.tvTran.text = translation
        holder.ivVoice.setOnClickListener {
            val voicePlay = MediaPlayer.create(holder.itemView.context, voice)
            voicePlay.start()
        }
    }

}