package com.zeeshan.tawkto.ui.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.zeeshan.tawkto.models.UserDetail
import com.zeeshan.tawkto.repository.UserRepository
import com.zeeshan.tawkto.utils.Resource

class UserDetailViewModel @ViewModelInject constructor(val repository: UserRepository) :
    ViewModel() {

    private val username = MutableLiveData<String>()
    private val userDetail = username.switchMap { username ->
        repository.getUserDetail(username)
    }

    val userDetailLiveData: LiveData<Resource<UserDetail>> = userDetail

    fun setUsername(username: String) {
        this.username.value = username
    }
}