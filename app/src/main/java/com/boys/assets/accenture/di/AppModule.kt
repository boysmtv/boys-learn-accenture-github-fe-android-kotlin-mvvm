package com.boys.assets.accenture.di

import com.boys.assets.accenture.activity.fragment.popular.vm.PopularViewModel
import com.boys.assets.accenture.activity.users.vm.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureStag = module {
    // for get users
    viewModel { UsersViewModel(get(), get()) }
    single { getUsersUseCase(get()) }
    single { getUsersRepoUseCase(get()) }

    viewModel { PopularViewModel(get(), get(), get()) }
    single { getPopularUseCase(get()) }
    single { getPopularAllUseCase(get()) }

    // create repository
    single { createRepository(get(), get()) }
}
