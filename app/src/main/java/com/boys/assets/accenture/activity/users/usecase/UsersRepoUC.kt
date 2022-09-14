package com.boys.assets.accenture.activity.users.usecase

import com.boys.assets.accenture.activity.users.model.ReposModel
import com.boys.assets.accenture.di.network.Repository
import com.boys.assets.accenture.domain.usecase.UseCase

class UsersRepoUC constructor(
    private val repository: Repository
) : UseCase<ArrayList<ReposModel>, Any?>() {

    private val TAG = this::class.java.simpleName

    override suspend fun run(params: Any?): ArrayList<ReposModel> {
        val token = "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"
        return repository.getRepos(token, params as String)
    }

}