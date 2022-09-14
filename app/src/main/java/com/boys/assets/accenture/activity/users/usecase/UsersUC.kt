package com.boys.assets.accenture.activity.users.usecase

import com.boys.assets.accenture.activity.users.model.UsersModel
import com.boys.assets.accenture.di.network.Repository
import com.boys.assets.accenture.domain.usecase.UseCase

class UsersUC constructor(
    private val repository: Repository
) : UseCase<UsersModel, Any?>() {

    private val TAG = this::class.java.simpleName

    override suspend fun run(params: Any?): UsersModel {
        val token = "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"
        return repository.getUsers(token, params as String)
    }

}