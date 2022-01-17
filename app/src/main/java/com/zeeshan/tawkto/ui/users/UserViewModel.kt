package com.zeeshan.tawkto.ui.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.zeeshan.tawkto.models.UserDetail
import com.zeeshan.tawkto.repository.UserRepository
import com.zeeshan.tawkto.room.entity.User
import com.zeeshan.tawkto.utils.Resource
import javax.inject.Inject

class UserViewModel @ViewModelInject constructor(private val repository: UserRepository) :
    ViewModel() {
    var search: String? = null


    private val since = MutableLiveData<Int>()
    private val users = since.switchMap { since ->
        repository.getUsers(since)
    }

    val usersLiveData: LiveData<Resource<List<User>>> = users

    fun setSince(since: Int) {
        this.since.value = since
    }

}