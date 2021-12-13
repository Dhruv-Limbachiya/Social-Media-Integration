package com.example.socialmediaintegration.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created By Dhruv Limbachiya on 08-12-2021 01:57 PM.
 */

@Parcelize
data class User(
    var fullName: String?,
    var email: String?,
    var image: String?,
    var id: String?
) : Parcelable
