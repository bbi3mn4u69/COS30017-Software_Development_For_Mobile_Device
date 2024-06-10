package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val name: String,
    val des: String,
    val price: Int,
    var isBorrow: Boolean?,
    var rentDay: Int?,
    var rentPrice: Int?,
    var attr1: String,
    var attr2: String,
    var attr3: String,
    var rate: Float,

    ): Parcelable {
}