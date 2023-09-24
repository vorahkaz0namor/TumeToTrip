package ru.sign.conditional.timetotrip.util

import okhttp3.internal.http.HTTP_FORBIDDEN
import okhttp3.internal.http.HTTP_NOT_FOUND
import okhttp3.internal.http.HTTP_NO_CONTENT
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import retrofit2.HttpException
import java.net.ConnectException

object CustomHelper {
    /** `520 Unknown Error` (non-standard HTTP code CloudFlare) */
    const val HTTP_UNKNOWN_ERROR = 520
    /** `444 Connection Failed` (thought up code) */
    private const val HTTP_CONNECTION_FAILED = 444
    /** Definition of the exception code */
    val exceptionCode = { e: Exception ->
        when (e) {
            is HttpException -> e.code()
            is ConnectException -> HTTP_CONNECTION_FAILED
            else -> HTTP_UNKNOWN_ERROR
        }
    }
    val codeOverview = { code: Int ->
        when (code) {
            in 200..299 -> if (code == HTTP_NO_CONTENT)
                "Body is null"
            else
                "Successful"
            in 400..499 -> when (code) {
                HTTP_CONNECTION_FAILED -> "Connection failed"
                HTTP_NOT_FOUND -> "Not found"
                HTTP_FORBIDDEN -> "Forbidden"
                HTTP_UNAUTHORIZED -> "Unauthorized"
                else -> "Bad request"
            }
            in 500..599 -> if (code == HTTP_UNKNOWN_ERROR)
                "Unkown error"
            else
                "Internal server error"
            else -> "Continue..."
        }
    }
}