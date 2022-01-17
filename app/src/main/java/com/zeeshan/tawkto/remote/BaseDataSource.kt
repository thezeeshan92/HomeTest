package com.zeeshan.tawkto.remote

import com.zeeshan.tawkto.utils.Resource
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (ex: Exception) {
            return when (ex) {
                is IOException -> error("Network Failure")
                else -> error(
                    ex.message ?: "Network call has failed for a following reason:  $ex.toString()"
                )
            }
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.tag("tawk.to.logs").d(message)
        return Resource.error(message)
    }

}