package com.boys.assets.accenture.activity.fragment.popular.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boys.assets.accenture.activity.fragment.popular.model.PopularModel
import com.boys.assets.accenture.activity.fragment.popular.model.PopularReqModel
import com.boys.assets.accenture.activity.fragment.popular.model.Users
import com.boys.assets.accenture.activity.fragment.popular.usecase.PopularAllUC
import com.boys.assets.accenture.activity.fragment.popular.usecase.PopularUC
import com.boys.assets.accenture.di.db.UsersDao
import com.boys.assets.accenture.domain.model.ApiError
import com.boys.assets.accenture.domain.usecase.UseCaseResponse
import com.boys.assets.accenture.utils.LogUtil
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PopularViewModel (
    private val popularAllUC: PopularAllUC,
    private val popularUC: PopularUC,
    private val usersDao: UsersDao
) : ViewModel()  {
    private val TAG = this::class.java.simpleName

    val onSuccess = MutableLiveData<List<Users>>()
    val onError = MutableLiveData<String>()

    fun doIt(reqModel: PopularReqModel) {
        popularUC.invoke(
            viewModelScope, reqModel,
            object : UseCaseResponse<PopularModel> {
                override fun onSuccess(result: PopularModel) {
                    result.items?.let { it -> {
                        onSuccess.value = it
                    } }
                }
                override fun onError(apiError: ApiError) {
                    onError.value = apiError.getErrorMessage()
                }
            },
        )
    }

    fun doIt() {
        popularAllUC.invoke(
            viewModelScope, null,
            object : UseCaseResponse<List<Users>> {
                override fun onSuccess(result: List<Users>) {
                    result.let { onSuccess.value = it }
                }
                override fun onError(apiError: ApiError) {
                    onError.value = apiError.getErrorMessage()
                }
            },
        )
    }

    fun getID(users: Int): Int = runBlocking {
        usersDao.getByID(users)
    }
    fun getAll(): List<Users> = runBlocking {
        usersDao.getAll()
    }
    fun addFav(users: Users){
        CoroutineScope(Dispatchers.IO).launch {
            usersDao.insert(users)
        }
    }
    fun delFav(users: Users){
        CoroutineScope(Dispatchers.IO).launch {
            usersDao.delete(users)
        }
    }
}