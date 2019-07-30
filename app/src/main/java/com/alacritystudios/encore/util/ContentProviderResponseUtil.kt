package com.alacritystudios.encore.util

class ContentProviderResponseUtil<T> {

    var body: T? = null
    lateinit var contentProviderState: ContentProviderState
    lateinit var message: String

    companion object {
        fun <T> createLoadingResponse(emptyCase: T): ContentProviderResponseUtil<T> {
            var contentProviderResponseUtil: ContentProviderResponseUtil<T> = ContentProviderResponseUtil()
            contentProviderResponseUtil.contentProviderState = ContentProviderState.LOADING
            contentProviderResponseUtil.body = emptyCase
            return contentProviderResponseUtil
        }

        fun <T> createSuccessResponse(emptyCase: T, response: T?): ContentProviderResponseUtil<T> {
            var contentProviderResponseUtil: ContentProviderResponseUtil<T> = ContentProviderResponseUtil()
            response?.let { result ->
                contentProviderResponseUtil.body = result ?: emptyCase
                contentProviderResponseUtil.contentProviderState = ContentProviderState.LOADED
                contentProviderResponseUtil.message = "Response body is LOADED."
            } ?: run {
                contentProviderResponseUtil.message = "An internal error has occurred."
                contentProviderResponseUtil.contentProviderState = ContentProviderState.ERROR
            }
            return contentProviderResponseUtil
        }

        fun <T> createFailureResponse(throwable: Throwable): ContentProviderResponseUtil<T> {
            var contentProviderResponseUtil: ContentProviderResponseUtil<T> = ContentProviderResponseUtil()
            throwable.message?.let { errorMessage ->
                contentProviderResponseUtil.message = errorMessage
                contentProviderResponseUtil.contentProviderState = ContentProviderState.ERROR
            } ?: run {
                contentProviderResponseUtil.message = "An internal error has occurredr"
                contentProviderResponseUtil.contentProviderState = ContentProviderState.ERROR
            }
            return contentProviderResponseUtil
        }
    }
}