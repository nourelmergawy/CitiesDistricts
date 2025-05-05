package com.bosta.citiesdistricts.common.data.repository.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.QueryMap

@JvmSuppressWildcards
interface BostaApiService {
    @GET("{path}")
    suspend fun get(
        @Path("path", encoded = true) pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): Response<ResponseBody>

    @POST("{path}")
    suspend fun post(
        @Path("path", encoded = true) pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>,
        @Body requestBody: Any
    ): Response<ResponseBody>

    @PUT("{path}")
    suspend fun put(
        @Path("path", encoded = true) pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>,
        @Body requestBody: Any
    ): Response<ResponseBody>

    @DELETE("{path}")
    suspend fun delete(
        @Path("path", encoded = true) pathUrl: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): Response<ResponseBody>

    @Multipart
    @POST("{path}")
    @JvmSuppressWildcards
    suspend fun postWithFiles(
        @Path("path", encoded = true) pathUrl: String,
        @QueryMap queryParams: Map<String, Any>,
        @HeaderMap headers: Map<String, Any>,
        @PartMap requestBodyMap: Map<String, RequestBody>,
        @Part files: List<MultipartBody.Part>,
    ): Response<ResponseBody>
}