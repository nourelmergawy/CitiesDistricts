package com.bosta.citiesdistricts.common.data.repository.remote

import com.bosta.citiesdistricts.android.extensions.fromJson
import com.bosta.citiesdistricts.common.data.models.exception.BostaException
import com.bosta.citiesdistricts.common.data.models.response.ErrorResponse
import com.bosta.citiesdistricts.common.domain.repository.remote.IRestApiNetworkProvider
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.reflect.Type
import java.net.HttpURLConnection

class RetrofitRestApiNetworkProvider(private val apiService: BostaApiService) :
    IRestApiNetworkProvider {
    override suspend fun <ResponseBody> get(
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        responseType: Type
    ): ResponseBody {
        val response = apiService.get(
            pathUrl = pathUrl,
            headers = headers ?: mapOf(),
            queryParams = queryParams ?: mapOf()
        )
        return handleResponse(response, responseType)
    }

    override suspend fun <RequestBody, ResponseBody> post(
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?,
        responseType: Type
    ): ResponseBody {
        val response = apiService.post(
            pathUrl = pathUrl,
            headers = headers ?: mapOf(),
            queryParams = queryParams ?: mapOf(),
            requestBody = requestBody ?: Unit
        )
        return handleResponse(response, responseType)
    }

    override suspend fun <RequestBody, ResponseBody> put(
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        requestBody: RequestBody?,
        responseType: Type
    ): ResponseBody {
        val response = apiService.put(
            pathUrl = pathUrl,
            headers = headers ?: mapOf(),
            queryParams = queryParams ?: mapOf(),
            requestBody = requestBody ?: Unit
        )
        return handleResponse(response, responseType)
    }

    override suspend fun <ResponseBody> delete(
        pathUrl: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        responseType: Type,
    ): ResponseBody {
        val response = apiService.delete(
            pathUrl = pathUrl,
            headers = headers ?: mapOf(),
            queryParams = queryParams ?: mapOf(),
        )
        return handleResponse(response, responseType)
    }

    private fun <ResponseType> handleResponse(
        response: Response<ResponseBody>,
        responseType: Type
    ): ResponseType {
        return if (response.isSuccessful) {
            val responseBody = response.body() ?: throw BostaException.UnexpectedHttpException(
                httpErrorCode = response.code(),
                errorMessage = "Response body is null"
            )
            responseBody.string().fromJson(responseType)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = errorBody ?: "Unknown error"
            when (response.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> throw BostaException.Client.UnauthorizedAccess(
                    errorMessage = errorMessage
                )

                HttpURLConnection.HTTP_NOT_FOUND -> throw BostaException.Client.NotFound(
                    errorMessage = errorMessage
                )

                HttpURLConnection.HTTP_BAD_REQUEST, 422 -> {
                    val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                    val errors =
                        errorResponse.errors?.mapValues { it.value.joinToString() } ?: emptyMap()
                    throw BostaException.Client.ResponseValidation(
                        fieldErrors = errors,
                        errorMessage = errorResponse.message
                    )
                }

                HttpURLConnection.HTTP_UNAVAILABLE, HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> throw BostaException.Server.RetryableServerException(
                    errorMessage = errorMessage
                )

                in 500..599 -> throw BostaException.Server.NonRetryableServerException(
                    errorMessage = errorMessage
                )

                else -> throw BostaException.UnexpectedHttpException(
                    httpErrorCode = response.code(),
                    errorMessage = errorMessage
                )
            }
        }
    }
}