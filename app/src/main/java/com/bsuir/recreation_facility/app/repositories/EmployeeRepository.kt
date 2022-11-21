package com.bsuir.recreation_facility.app.repositories

import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.sources.exceptions.BackendException
import com.bsuir.recreation_facility.sources.exceptions.InvalidCredentialsException
import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.user.UserSource
import retrofit2.Response

typealias EmployeeListener = (message: String) -> Unit

class EmployeeRepository(
    private val employeeSource: EmployeeSource
) {

        suspend fun login(employee: Employee): Response<Employee> {
            val res: Response<Employee> = try {
                employeeSource.login(employee)
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