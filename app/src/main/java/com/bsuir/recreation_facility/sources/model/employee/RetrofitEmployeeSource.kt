package com.bsuir.recreation_facility.sources.model.employee

import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.sources.backend.BackendRetrofitSource
import com.bsuir.recreation_facility.sources.backend.RetrofitConfig
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

    override suspend fun getEmployee(currentUsername: String?): Response<Employee> = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.getEmployee(currentUsername!!)
    }

    override suspend fun getAListOfUnregisteredUsers(): Response<List<User>> = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.getAListOfUnregisteredUsers()
    }

    override suspend fun registerEmployee(username: String): Response<List<User>>  = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.registerEmployee(username)
    }

    override suspend fun deleteUser(username: String): Response<List<User>> = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.deleteUser(username)
    }

    override suspend fun getUserDetails(username: String): Response<User> = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.getUserDetails(username)
    }

    override suspend fun getAListOfUnregisteredEmployees(username: String): Response<List<Employee>>  = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.getAListOfUnregisteredEmployees(username)
    }

    override suspend fun setPosition(
        username: String,
        employeename: String,
        flag: Boolean
    ): Response<List<Employee>> = wrapRetrofitExceptions  {
        delay(1000)
        employeeApi.setPosition(username, employeename, flag)
    }

    override suspend fun addStimulation(
        employeename: String,
        stimulation: Stimulation,
    ): Response<List<Employee>> = wrapRetrofitExceptions {
        delay(1000)
        employeeApi.addStimulation(employeename, stimulation)
    }

    override suspend fun getFriends(username: String): Response<List<Employee>> = wrapRetrofitExceptions  {
        delay(1000)
        employeeApi.getFriends(username)
    }

}