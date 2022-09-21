package com.boys.assets.accenture.activity.fragment.popular.usecase

import com.boys.assets.accenture.BuildConfig
import com.boys.assets.accenture.activity.fragment.popular.model.PopularModel
import com.boys.assets.accenture.activity.fragment.popular.model.PopularReqModel
import com.boys.assets.accenture.di.network.Repository
import com.boys.assets.accenture.domain.usecase.UseCase

class PopularUC constructor(
    private val repository: Repository
) : UseCase<PopularModel, Any?>() {

    private val TAG = this::class.java.simpleName

    override suspend fun run(params: Any?): PopularModel {
        return repository.getPopular(BuildConfig.TOKEN_GITHUB, params as PopularReqModel)
    }

}