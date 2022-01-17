package com.zeeshan.tawkto.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val webservice: Webservice) :
    BaseDataSource() {

    suspend fun getUsers(since: Int) = getResult { webservice.users(since) }

    suspend fun getUserDetail(username: String) = getResult { webservice.userDetail(username) }

}