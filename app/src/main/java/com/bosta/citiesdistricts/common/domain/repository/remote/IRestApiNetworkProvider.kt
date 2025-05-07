package com.bosta.citiesdistricts.common.domain.repository.remote

import java.lang.reflect.Type

interface IRestApiNetworkProvider {
    suspend fun <ResponseBody> get(
        pathUrl: String,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        responseType: Type
    ): ResponseBody

    suspend fun <RequestBody, ResponseBody> post(
        pathUrl: String,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        requestBody: RequestBody? = null,
        responseType: Type
    ): ResponseBody

    suspend fun <RequestBody, ResponseBody> put(
        pathUrl: String,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        requestBody: RequestBody? = null,
        responseType: Type
    ): ResponseBody

    suspend fun <ResponseBody> delete(
        pathUrl: String,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        responseType: Type,
    ): ResponseBody

}