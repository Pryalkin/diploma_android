package com.bsuir.recreation_facility.sources.model.employee

import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.User
import retrofit2.Response

interface EmployeeSource {
    suspend fun login(employee: Employee): Response<Employee>
    suspend fun getEmployee(currentUsername: String?): Response<Employee>
    suspend fun getAListOfUnregisteredUsers(): Response<List<User>>
    suspend fun registerEmployee(username: String): Response<List<User>>
    suspend fun deleteUser(username: String): Response<List<User>>
    suspend fun getUserDetails(username: String): Response<User>
    suspend fun getAListOfUnregisteredEmployees(username: String): Response<List<Employee>>
    suspend fun setPosition(username: String, employeename: String, flag: Boolean): Response<List<Employee>>
    suspend fun addStimulation(employeename: String, stimulation: Stimulation): Response<List<Employee>>
    suspend fun getFriends(username: String): Response<List<Employee>>
}