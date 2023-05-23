package com.bsuir.recreation_facility.sources.model.employee

import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.User
import retrofit2.Response
import retrofit2.http.*

interface EmployeeApi {

    @POST("employee/login")
    suspend fun login(@Body employee: Employee): Response<Employee>

    @GET("employee/get")
    suspend fun getEmployee(@Query("username") username: String): Response<Employee>

    @GET("user/get/registration")
    suspend fun getAListOfUnregisteredUsers(): Response<List<User>>

    @GET("employee/set/registration")
    suspend fun registerEmployee(@Query("username") username: String): Response<List<User>>

    @GET("user/delete")
    suspend fun deleteUser(@Query("username") username: String): Response<List<User>>

    @GET("user/get")
    suspend fun getUserDetails(@Query("username") username: String): Response<User>

    @GET("employee/get/registration")
    suspend fun getAListOfUnregisteredEmployees(@Query("username") username: String): Response<List<Employee>>

    @GET("employee/set/position")
    suspend fun setPosition(@Query("username") username: String,
                            @Query("employeename") employeename: String,
                            @Query("flag") flag: Boolean): Response<List<Employee>>

    @POST("employee/set/stimulation/{employeename}")
    suspend fun addStimulation(@Path("employeename") employeename: String,
                               @Body stimulation: Stimulation): Response<List<Employee>>

    @GET("employee/friends")
    suspend fun getFriends(@Query("username") username: String): Response<List<Employee>>

}