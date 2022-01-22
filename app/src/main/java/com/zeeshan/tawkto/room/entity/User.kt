package com.zeeshan.tawkto.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @field:SerializedName("following_url")
    val followingUrl: String? = null,

    @field:SerializedName("login")
    val login: String = "",

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("events_url")
    val eventsUrl: String? = null,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int = 0,

    val note: String = ""
)