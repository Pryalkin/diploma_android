package com.bsuir.recreation_facility.sources.model.user

import com.bsuir.recreation_facility.app.model.User
import retrofit2.Response

interface UserSource {
    suspend fun addUser(user: User): Response<User>
}
