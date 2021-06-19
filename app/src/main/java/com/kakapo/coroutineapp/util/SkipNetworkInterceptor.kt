package com.kakapo.coroutineapp.util

import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

private var FAKE_RESULTS = listOf(
    "Hello Coroutine",
    "My Favorite Feature",
    "Async Made Easy",
    "Coroutine Bu Example",
    "Check out the Advanced Coroutines Code lab Next"
)

class SkipNetworkInterceptor : Interceptor{
    private var lastResult: String = ""
    val gson = Gson()

    private var attempts = 0;

    private fun wantRandomError() = attempts++ % 5 == 0

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }


    private fun pretendToBlockForNetworkRequest() = Thread.sleep(500)

    //TODO lek error ndek kene '<'
    private fun makeErrorResults(request: Request): Response{
        return Response.Builder()
            .code(500)
            .protocol(Protocol.HTTP_1_0)
            .message("Bad server")
            .body(
                gson.toJson(mapOf("cause" to "not suer"))
                    .toResponseBody("application/json".toMediaType())
            )
            .build()
    }

    private fun makeOkResult(request: Request) : Response{
        var nextResult = lastResult
        while (nextResult == lastResult){
            nextResult = FAKE_RESULTS.random()
        }
        lastResult = nextResult

        return Response.Builder()
            .code(200)
            .request(request)
            .protocol(Protocol.HTTP_1_0)
            .message("OK")
            .body(
                gson.toJson(nextResult)
                    .toResponseBody("application/json".toMediaType())
            )
            .build()
    }
}