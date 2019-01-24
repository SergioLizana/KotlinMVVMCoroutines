package com.rivia.software.modelapimyjson

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiMyJson {

    @GET("/bins/1a30k8")
    fun getList(): Deferred<Response<MutableList<Data>>>
}