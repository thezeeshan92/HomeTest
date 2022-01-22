package com.zeeshan.tawkto.repository

import androidx.lifecycle.LiveData
import com.zeeshan.tawkto.remote.UserRemoteDataSource
import com.zeeshan.tawkto.room.dao.UserDao
import com.zeeshan.tawkto.utils.performGetUserDetailOperation
import com.zeeshan.tawkto.utils.performGetUserOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getUsers(since: Int) = performGetUserOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { userRemoteDataSource.getUsers(since) },
        saveCallResult = {
            if (!it.isNullOrEmpty())
                localDataSource.insertAll(it)
        }
    )

    fun getUserDetail(username: String) = performGetUserDetailOperation(
        networkCall = { userRemoteDataSource.getUserDetail(username) }
    )

    fun getUserNote(username: String): LiveData<String> {
        return localDataSource.getSingleUserNote(username)
    }

    fun updateUserNote(username: String, note: String) {
        CoroutineScope(Dispatchers.IO).launch { localDataSource.updateNote(username, note) }
    }
}