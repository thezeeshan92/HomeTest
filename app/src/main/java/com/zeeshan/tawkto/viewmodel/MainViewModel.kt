package com.zeeshan.tawkto.viewmodel

class MainViewModel {}
/*
@Inject
constructor(private val userRepository: UserRepository) : ViewModel() {
    var since: Int? = 0
    var search: String? = null
    var loading = MutableLiveData<Boolean>()
    var usersLoadError = MutableLiveData<String?>()
    private var job: Job? = null

    var usersList = MutableLiveData<List<UserItem?>>()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun performSearch() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = userRepository.getSearchResult(since!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    usersList.value = response.body()?.response!!
                    usersLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
        usersLoadError.value = ""
        loading.value = false
    }

    private fun onError(message: String) {
        usersLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}*/
