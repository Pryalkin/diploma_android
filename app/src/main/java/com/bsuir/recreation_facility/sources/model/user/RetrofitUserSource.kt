package com.bsuir.recreation_facility.sources.model.user

import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.sources.backend.BackendRetrofitSource
import com.bsuir.recreation_facility.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitUserSource(
    config: RetrofitConfig
) : BackendRetrofitSource(config), UserSource{

    private val userApi = retrofit.create(UserApi::class.java)

    override suspend fun registration(user: User): Response<User> = wrapRetrofitExceptions{
        delay(1000)
        userApi.registration(user)
    }

}