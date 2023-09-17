package ru.sign.conditional.timetotrip.util

import retrofit2.HttpException
import java.net.ConnectException

object CustomHelper {
    /** `520 Unknown Error` (non-standard HTTP code CloudFlare) */
    private const val HTTP_UNKNOWN_ERROR = 520
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
}