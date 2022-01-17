package com.zeeshan.tawkto.remote

import com.zeeshan.tawkto.models.UserDetail
import com.zeeshan.tawkto.room.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {

    @GET("users")
    suspend fun users(@Query("since") since: Int): Response<List<User>>

    @GET("users/{username}")
    suspend fun userDetail(@Path("username") username: String): Response<UserDetail>
}