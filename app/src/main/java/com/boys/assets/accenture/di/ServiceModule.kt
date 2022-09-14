package com.boys.assets.accenture.di

import com.boys.assets.accenture.remote.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { createService(get()) }
}

fun createService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}