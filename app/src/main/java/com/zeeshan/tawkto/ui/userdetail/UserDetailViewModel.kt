package com.zeeshan.tawkto.ui.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.zeeshan.tawkto.models.UserDetail
import com.zeeshan.tawkto.repository.UserRepository
import com.zeeshan.tawkto.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class UserDetailViewModel @ViewModelInject constructor(val repository: UserRepository) :
    ViewModel() {

    private val username = MutableLiveData<String>()
    var userNoteLiveData: LiveData<String> = MutableLiveData()
    var notes = ""
    private val userDetail = username.switchMap { username ->
        repository.getUserDetail(username)
    }

    val userDetailLiveData: LiveData<Resource<UserDetail>> = userDetail

    fun setUsername(username: String) {
        this.username.value = username

        CoroutineScope(Dispatchers.IO).launch {
            userNoteLiveData = repository.getUserNote(username)
        }
    }

    fun updateUserNote() {
        Timber.d(notes)
        repository.updateUserNote(username.value!!, notes)
    }
}