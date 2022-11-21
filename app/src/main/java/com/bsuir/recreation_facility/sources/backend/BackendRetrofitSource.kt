package com.bsuir.recreation_facility.sources.backend

import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.sources.exceptions.AppException
import com.bsuir.recreation_facility.sources.exceptions.BackendException
import com.bsuir.recreation_facility.sources.exceptions.ConnectionException
import com.bsuir.recreation_facility.sources.exceptions.ParseBackendResponseException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

open class BackendRetrofitSource(
    retrofitConfig: RetrofitConfig
) {
    val retrofit = retrofitConfig.retrofit
    private val moshi: Moshi = retrofitConfig.moshi
    private val errorAdapter = moshi.adapter(HttpResponse::class.java)

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: AppException) {
            throw e
            // moshi
        } catch (e: JsonDataException) {
            throw ParseBackendResponseException(e)
        } catch (e: JsonEncodingException) {
            throw ParseBackendResponseException(e)
            // retrofit
        } catch (e: HttpException) {
            throw Exception(e)
            throw createBackendException(e)
            // mostly retrofit
        } catch (e: IOException) {
            throw ConnectionException(e)
        }

    }

    private fun createBackendException(e: HttpException): Exception {
        return try {
            val errorBody: HttpResponse = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            BackendException(e.code(), errorBody.message)
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }

    class ErrorResponseBody(
        val error: String
    )

}