package com.boys.assets.accenture.di

import com.boys.assets.accenture.activity.fragment.popular.usecase.PopularAllUC
import com.boys.assets.accenture.activity.fragment.popular.usecase.PopularUC
import com.boys.assets.accenture.activity.users.usecase.UsersRepoUC
import com.boys.assets.accenture.activity.users.usecase.UsersUC
import com.boys.assets.accenture.di.network.Repository

fun getUsersUseCase(repository: Repository): UsersUC {
    return UsersUC(repository)
}

fun getUsersRepoUseCase(repository: Repository): UsersRepoUC {
    return UsersRepoUC(repository)
}

fun getPopularUseCase(repository: Repository): PopularUC {
    return PopularUC(repository)
}

fun getPopularAllUseCase(repository: Repository): PopularAllUC {
    return PopularAllUC(repository)
}
