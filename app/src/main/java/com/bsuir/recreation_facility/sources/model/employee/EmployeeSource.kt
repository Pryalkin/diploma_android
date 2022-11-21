package com.bsuir.recreation_facility.sources.model.employee

import com.bsuir.recreation_facility.app.model.Employee
import retrofit2.Response

interface EmployeeSource {
    suspend fun login(employee: Employee): Response<Employee>
}