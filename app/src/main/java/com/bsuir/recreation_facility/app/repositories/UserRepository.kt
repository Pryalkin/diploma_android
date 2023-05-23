package com.bsuir.recreation_facility.app.repositories

import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.sources.exceptions.BackendException
import com.bsuir.recreation_facility.sources.exceptions.InvalidCredentialsException
import com.bsuir.recreation_facility.sources.model.user.UserSource
import retrofit2.Response

typealias MessListener = (message: String) -> Unit

class UserRepository(
    private val userSource: UserSource
) {

    suspend fun registration(user: User): Response<User> {
        val res: Response<User> = try {
            userSource.registration(user)
        } catch (e: Exception) {
            if (e is BackendException && e.code == 401) {
                // map 401 error for sign-in to InvalidCredentialsException
                throw InvalidCredentialsException(e)
            } else {
                throw e
            }
        }
        return res
    }


}