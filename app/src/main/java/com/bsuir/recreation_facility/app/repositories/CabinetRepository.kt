package com.bsuir.recreation_facility.app.repositories

import com.bsuir.recreation_facility.app.model.*
import com.bsuir.recreation_facility.app.model.setting.AppSettings
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.sources.exceptions.BackendException
import com.bsuir.recreation_facility.sources.exceptions.InvalidCredentialsException
import com.bsuir.recreation_facility.sources.model.department.DepartmentSource
import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.room.RoomSource
import retrofit2.Response
import java.util.*

typealias EmployeeListener = (employee: Employee) -> Unit
typealias UsersListener = (users: List<User>) -> Unit
typealias EmployeesListener = (employees: List<Employee>) -> Unit
typealias UserListener = (users: User) -> Unit
typealias ErrorListener = (error: HttpResponse) -> Unit
typealias DepartmentListener = (department: Department) -> Unit
typealias RoomsListener = (rooms: List<Room>) -> Unit

class CabinetRepository(
    private val employeeSource: EmployeeSource,
    private val departmentSource: DepartmentSource,
    private val roomSource: RoomSource,
    private val appSettings: AppSettings
) {

    private var users = mutableListOf<User>()
    private val usersListener = mutableSetOf<UsersListener>()

    private var employees = mutableListOf<Employee>()
    private val employeesListener = mutableSetOf<EmployeesListener>()

    private var friends = mutableListOf<Employee>()
    private val friendsListener = mutableSetOf<EmployeesListener>()

    private var rooms = mutableListOf<Room>()
    private val roomsListener = mutableSetOf<RoomsListener>()

    suspend fun getEmployee(): Response<Employee> {
        val res: Response<Employee> = try {
            employeeSource.getEmployee(appSettings.getCurrentUsername())
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

    fun getRole(): String? {
        return appSettings.getCurrentRole()
    }

    suspend fun getAListOfUnregisteredUsers(): Response<List<User>> {
        val res: Response<List<User>> = try {
            employeeSource.getAListOfUnregisteredUsers()
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

    fun addListenerUsers(listener: UsersListener){
        usersListener.add(listener)
        listener.invoke(users)
    }

    fun removeListenerUsers(listener: UsersListener){
        usersListener.remove(listener)
    }

    private fun notifyChangesUsers(){
        usersListener.forEach {it.invoke(users)}
    }

    fun setUsersListener(body: List<User>) {
        users = body as MutableList<User>
        notifyChangesUsers()
    }

    suspend fun registerEmployee(username: String): Response<List<User>> {
        val res: Response<List<User>> = try {
            employeeSource.registerEmployee(username)
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

    suspend fun deleteUser(username: String): Response<List<User>> {
        val res: Response<List<User>> = try {
            employeeSource.deleteUser(username)
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

    suspend fun getUserDetails(username: String): Response<User> {
        val res: Response<User> = try {
            employeeSource.getUserDetails(username)
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

    fun addListenerEmployees(listener: EmployeesListener) {
        employeesListener.add(listener)
        listener.invoke(employees)
    }

    fun removeListenerEmployees(listener: EmployeesListener) {
        employeesListener.remove(listener)
    }

    fun setEmployeesListener(body: List<Employee>) {
        employees = body as MutableList<Employee>
        notifyChangesEmployees()
    }

    private fun notifyChangesEmployees(){
        employeesListener.forEach {it.invoke(employees)}
    }

    suspend fun getAListOfUnregisteredEmployees(): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            employeeSource.getAListOfUnregisteredEmployees(appSettings.getCurrentUsername()!!)
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

    suspend fun registerAnEmployeeInTheDepartment(username: String, employeename: String, name: String): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            departmentSource.registerAnEmployeeInTheDepartment(username, employeename, name)
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

    suspend fun getEmployeeDetails(username: String): Response<Employee> {
        val res: Response<Employee> = try {
            employeeSource.getEmployee(username)
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

    suspend fun deleteEmployeeFromDepartment(username: String, employeename: String): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            departmentSource.deleteEmployeeFromDepartment(username, employeename)
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

    fun getGroupname(): String {
        return appSettings.getCurrentGroupname()!!
    }

    fun getUsername(): String? {
        return appSettings.getCurrentUsername()
    }

    suspend fun addJobTitle(username: String, employeename: String, jobTitle: String): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            departmentSource.addJobTitle(username, employeename, jobTitle)
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

    suspend fun deleteJobTitle(username: String, employeename: String): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            departmentSource.deleteJobTitle(username, employeename)
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

    suspend fun setPosition(username: String, employeename: String, flag: Boolean): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            employeeSource.setPosition(username, employeename, flag)
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

    fun setSumma(sum: Float) {
        appSettings.setCurrentSumma(sum)
    }

    fun getSumma(): Float? {
        val summa = appSettings.getCurrentSumma()
//        return if (summa.isNullOrEmpty()) "0"
//        else summa
        return summa
    }

    suspend fun addStimulation(employeename: String, stimulation: Stimulation): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            employeeSource.addStimulation(employeename, stimulation)
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

    suspend fun createStimulation(employeename: String, message: String): Response<List<Employee>> {
        val user = User(
            0L, "", "", "",
            Date(), "", "", appSettings.getCurrentUsername()!!, "",
            Date(), Date(), false, isNotLocked = false, isActive = false
        )
        val employee = Employee(0L, user, null, null, null, null, null, null)
        val stimulation = Stimulation(0L, employee, message, appSettings.getCurrentSumma()!!, Date())
        println(stimulation.toString())
        val res: Response<List<Employee>> = try {
            employeeSource.addStimulation(employeename, stimulation)
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

    fun addListenerRooms(listener: RoomsListener){
        roomsListener.add(listener)
        listener.invoke(rooms)
    }

    fun removeListenerRooms(listener: RoomsListener){
        roomsListener.remove(listener)
    }

    private fun notifyChangesRooms(){
        roomsListener.forEach {it.invoke(rooms)}
    }

    fun setRoomsListener(body: List<Room>) {
        rooms = body as MutableList<Room>
        notifyChangesRooms()
    }

    suspend fun getCorrespondence(username: String): Response<List<Room>> {
        val res: Response<List<Room>> = try {
            roomSource.getCorrespondence(username)
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

    suspend fun getFriends(username: String): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            employeeSource.getFriends(username)
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

    fun addListenerFriends(listener: EmployeesListener) {
        friendsListener.add(listener)
        listener.invoke(friends)
    }

    fun removeListenerFriends(listener: EmployeesListener) {
        friendsListener.remove(listener)
    }

    fun setFriendsListener(body: List<Employee>) {
        friends = body as MutableList<Employee>
        notifyChangesFriends()
    }

    private fun notifyChangesFriends(){
        friendsListener.forEach {it.invoke(friends)}
    }

}