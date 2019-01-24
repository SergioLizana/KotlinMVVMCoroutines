package com.rivia.software.restmodule.callback

/**
 * This base custom callback receives two generic types:
 * one for OK responses and one for KO responses.
 *
 * It keeps the Http code if necessary
 */
abstract class BaseCallback<OkResponseType, KoResponseType> {

    var httpStatusCode: Int? = null

    abstract fun onResponseOk(response: OkResponseType?)

    abstract fun onResponseKo(errorResponse: KoResponseType?)

    abstract fun onResponseFail(t: Throwable?)

}