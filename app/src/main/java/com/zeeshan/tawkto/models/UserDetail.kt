package com.zeeshan.tawkto.models

import com.google.gson.annotations.SerializedName

data class UserDetail(

	@field:SerializedName("bio")
	val bio: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("blog")
	val blog: String = "",

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,


	@field:SerializedName("company")
	val company: String = "",

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("email")
	val email: Any? = null,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String = "",

	@field:SerializedName("starred_url")
	val starredUrl: String = "",

	@field:SerializedName("followers_url")
	val followersUrl: String = "",

	@field:SerializedName("public_gists")
	val publicGists: Int = 0,

	@field:SerializedName("url")
	val url: String = "",

	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String = "",

	@field:SerializedName("followers")
	val followers: Int = 0,

	@field:SerializedName("avatar_url")
	val avatarUrl: String = "",

	@field:SerializedName("following")
	val following: Int = 0,

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("location")
	val location: String = "",
)
