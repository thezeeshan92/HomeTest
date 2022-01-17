package com.zeeshan.tawkto.repository

import com.zeeshan.tawkto.remote.UserRemoteDataSource
import com.zeeshan.tawkto.room.dao.UserDao
import com.zeeshan.tawkto.utils.performGetUserDetailOperation
import com.zeeshan.tawkto.utils.performGetUserOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getUsers(since: Int) = performGetUserOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { userRemoteDataSource.getUsers(since) },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    fun getUserDetail(username: String) = performGetUserDetailOperation(
        networkCall = { userRemoteDataSource.getUserDetail(username) }
    )
}