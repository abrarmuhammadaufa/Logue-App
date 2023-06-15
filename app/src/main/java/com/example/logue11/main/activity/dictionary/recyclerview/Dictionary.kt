package com.example.logue11.main.activity.dictionary.recyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dictionary(
    val word: String,
    val translation: String,
    val voice: Int
) : Parcelable
