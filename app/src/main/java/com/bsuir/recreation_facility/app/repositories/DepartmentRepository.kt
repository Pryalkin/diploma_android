package com.bsuir.recreation_facility.app.repositories

import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.DepartmentName
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.setting.AppSettings
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.sources.exceptions.BackendException
import com.bsuir.recreation_facility.sources.exceptions.InvalidCredentialsException
import com.bsuir.recreation_facility.sources.model.department.DepartmentSource
import retrofit2.Response

typealias AnnouncementsListener = (announcements: List<Announcement>) -> Unit
typealias AnnouncementListener = (announcement: Announcement) -> Unit
typealias EmployeesForDepartmentListener = (employees: List<Employee>) -> Unit
typealias ErrorList = (error: HttpResponse) -> Unit
typealias DepartmentNamesListener = (departmentnames: List<DepartmentName>) -> Unit

class DepartmentRepository(
    private val departmentSource: DepartmentSource,
    private val appSettings: AppSettings
) {

    private var announcements = mutableListOf<Announcement>()
    private val announcementsListener = mutableSetOf<AnnouncementsListener>()

    private var employees = mutableListOf<Employee>()
    private val employeesListener = mutableSetOf<EmployeesForDepartmentListener>()

    private var departmentnames = mutableListOf<DepartmentName>()
    private val departmentnamesListener = mutableSetOf<DepartmentNamesListener>()

    fun getRole(): String? {
        return appSettings.getCurrentRole()
    }

    fun addListenerAnnouncements(listener: AnnouncementsListener) {
        announcementsListener.add(listener)
        listener.invoke(announcements)
    }

    fun removeListenerAnnouncements(listener: AnnouncementsListener){
        announcementsListener.remove(listener)
    }

    private fun notifyChangesAnnouncements(){
        announcementsListener.forEach {it.invoke(announcements)}
    }

    fun setAnnouncementsListener(body: List<Announcement>) {
        announcements = body as MutableList<Announcement>
        notifyChangesAnnouncements()
    }

    fun getGroupname(): String {
        return appSettings.getCurrentGroupname()!!
    }

    suspend fun getAnnouncements(groupname: String): Response<List<Announcement>> {
        val res: Response<List<Announcement>> = try {
            departmentSource.getAnnouncements(groupname)
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

    suspend fun createAd(groupname: String, announcement: Announcement): Response<HttpResponse> {
        val res: Response<HttpResponse> = try {
            departmentSource.createAd(groupname, announcement)
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

    fun getUsername(): String? {
        return appSettings.getCurrentUsername()
    }

    suspend fun setEmotion(
        announcement: Announcement,
        emotion: String,
        username: String,
        groupname: String
    ): Response<List<Announcement>> {
        val res: Response<List<Announcement>> = try {
            departmentSource.setEmotion(announcement, emotion, username, groupname)
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

    fun addListenerEmployees(listener: EmployeesForDepartmentListener) {
        employeesListener.add(listener)
        listener.invoke(employees)
    }

    fun removeListenerEmployees(listener: EmployeesForDepartmentListener){
        employeesListener.remove(listener)
    }

    private fun notifyChangesEmployees(){
        employeesListener.forEach {it.invoke(employees)}
    }

    fun setEmployeesListener(body: List<Employee>) {
        employees = body as MutableList<Employee>
        notifyChangesEmployees()
    }

    suspend fun getTheEmployeeDepartment(groupname: String): Response<List<Employee>> {
        val res: Response<List<Employee>> = try {
            departmentSource.getTheEmployeesDepartment(groupname, appSettings.getCurrentUsername()!!)
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

    fun addListenerDepartmentNames(listener: DepartmentNamesListener) {
        departmentnamesListener.add(listener)
        listener.invoke(departmentnames)
    }

    fun removeListenerDepartmentNames(listener: DepartmentNamesListener){
        departmentnamesListener.remove(listener)
    }

    private fun notifyChangesDepartmentNames(){
        departmentnamesListener.forEach {it.invoke(departmentnames)}
    }

    fun setDepartmentNamesListener(body: List<DepartmentName>) {
        departmentnames = body as MutableList<DepartmentName>
        notifyChangesDepartmentNames()
    }

    suspend fun getDepartmentName(): Response<List<DepartmentName>> {
        val res: Response<List<DepartmentName>> = try {
            departmentSource.getDepartmentName()
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

    suspend fun getAnnouncement(id: Long): Response<Announcement> {
        val res: Response<Announcement> = try {
            departmentSource.getAnnouncement(id)
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