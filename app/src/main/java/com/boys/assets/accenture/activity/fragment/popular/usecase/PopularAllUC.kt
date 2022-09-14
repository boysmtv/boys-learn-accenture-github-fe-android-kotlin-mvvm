package com.boys.assets.accenture.activity.fragment.popular.usecase

import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.di.network.Repository
import com.boys.assets.accenture.domain.usecase.UseCase

class PopularAllUC constructor(
    private val repository: Repository
) : UseCase<List<Users>, Any?>() {

    private val TAG = this::class.java.simpleName

    override suspend fun run(params: Any?): List<Users> {
        return repository.getAllUsers()
    }

}