package com.alacritystudios.encore.util

import retrofit2.Response

class NetworkResponseUtil<T> {

    var body: T? = null
    lateinit var networkState: NetworkState
    lateinit var message: String

    companion object {
        fun <T> createLoadingResponse(): NetworkResponseUtil<T> {
            var networkResponseUtil: NetworkResponseUtil<T> = NetworkResponseUtil()
            networkResponseUtil.networkState = NetworkState.LOADING
            return networkResponseUtil
        }

        fun <T> createSuccessResponse(response: Response<T>?): NetworkResponseUtil<T> {
            var networkResponseUtil: NetworkResponseUtil<T> = NetworkResponseUtil()
            response?.let { result ->
                if (result.isSuccessful) {
                    result.body()?.let { bodyObject ->
                        networkResponseUtil.body = bodyObject
                        networkResponseUtil.networkState = NetworkState.LOADED
                        networkResponseUtil.message = "Response body is LOADED."
                    } ?: run {
                        networkResponseUtil.message = "Response body is NULL."
                        networkResponseUtil.networkState = NetworkState.ERROR
                    }
                } else {
                    networkResponseUtil.message = result.message()
                    networkResponseUtil.networkState = NetworkState.ERROR
                }
            } ?: run {
                networkResponseUtil.message = "Response is NULL."
                networkResponseUtil.networkState = NetworkState.ERROR
            }
            return networkResponseUtil
        }

        fun <T> createFailureResponse(throwable: Throwable): NetworkResponseUtil<T> {
            var networkResponseUtil: NetworkResponseUtil<T> = NetworkResponseUtil()
            throwable.message?.let { errorMessage ->
                networkResponseUtil.message = errorMessage
                networkResponseUtil.networkState = NetworkState.ERROR
            } ?: run {
                networkResponseUtil.message = "Server Internal Error"
                networkResponseUtil.networkState = NetworkState.ERROR
            }
            return networkResponseUtil
        }
    }
}