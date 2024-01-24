package com.jjbaksa.data.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

suspend fun <DATA, DOMAIN> apiCall(
    call: suspend () -> DATA,
    remoteData: suspend(DATA) -> Unit = {},
    mapper: (DATA) -> DOMAIN,
): Flow<Result<DOMAIN>> = call.asFlow()
    .map {
        remoteData(it)
        Result.success(mapper(it))
    }.catch { emit(Result.failure(it)) }
    .flowOn(Dispatchers.IO)
