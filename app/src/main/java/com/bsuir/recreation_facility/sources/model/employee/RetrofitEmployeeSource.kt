package com.bsuir.recreation_facility.sources.model.employee

import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.sources.backend.BackendRetrofitSource
import com.bsuir.recreation_facility.sources.backend.RetrofitConfig
import com.bsuir.recreation_facility.sources.model.user.UserApi
import com.bsuir.recreation_facility.sources.model.user.UserSource
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitEmployeeSource (
    config: RetrofitConfig
) : BackendRetrofitSource(config), EmployeeSource {

    private val employeeApi = retrofit.create(EmployeeApi::class.java)

    override suspend fun login(employee: Employee): Response<Employee> = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.login(employee)
    }


}