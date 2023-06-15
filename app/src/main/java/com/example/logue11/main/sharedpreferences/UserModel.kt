package com.example.logue11.main.sharedpreferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    var userid: String? = null,
    var name: String? = null,
    var token: String? = null,
): Parcelable
