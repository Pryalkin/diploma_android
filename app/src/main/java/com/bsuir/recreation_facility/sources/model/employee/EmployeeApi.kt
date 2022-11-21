package com.bsuir.recreation_facility.sources.model.employee

import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EmployeeApi {

    @POST("employee/login")
    suspend fun login(@Body employee: Employee): Response<Employee>
}