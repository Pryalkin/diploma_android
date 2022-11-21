package com.bsuir.recreation_facility.sources.model.user

import com.bsuir.recreation_facility.app.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("user/register")
    suspend fun addUser(@Body user: User): Response<User>
}