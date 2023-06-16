package com.example.logue11.main.activity.dictionary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logue11.R
import com.example.logue11.databinding.ActivityDictionaryBinding
import com.example.logue11.main.activity.dictionary.recyclerview.Dictionary
import com.example.logue11.main.activity.dictionary.recyclerview.DictionaryAdapter

class DictionaryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDictionaryBinding
    private val list = ArrayList<Dictionary>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDictionaryBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)
        supportActionBar?.title=("Vocabulary")

        list.addAll(getWordList())
        showRecyclerView()

    }

    @SuppressLint("Recycle")
    private fun getWordList(): ArrayList<Dictionary> {
        val word = resources.getStringArray(R.array.word_one)
        val translation = resources.getStringArray(R.array.translate_one)
        val voice = resources.obtainTypedArray(R.array.voice_one)
        val listWord = ArrayList<Dictionary>()
        for (i in word.indices) {
            val dictionary =
                Dictionary(word[i], translation[i], voice.getResourceId(i, -1))
            listWord.add(dictionary)
        }
        return listWord
    }

    private fun showRecyclerView() {
        binding.rvWord.layoutManager = LinearLayoutManager(this)
        val listWordAdapter = DictionaryAdapter(list)
        binding.rvWord.adapter = listWordAdapter
    }
}