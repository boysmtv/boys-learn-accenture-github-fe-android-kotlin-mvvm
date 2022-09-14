package com.boys.assets.accenture.domain.usecase

import com.boys.assets.accenture.domain.model.ApiError

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError)

}

