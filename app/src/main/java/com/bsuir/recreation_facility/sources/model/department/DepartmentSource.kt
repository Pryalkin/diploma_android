package com.bsuir.recreation_facility.sources.model.department

import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.DepartmentName
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import retrofit2.Response

interface DepartmentSource {
    suspend fun registerAnEmployeeInTheDepartment(username: String, employeename: String, departmentName: String): Response<List<Employee>>
    suspend fun deleteEmployeeFromDepartment(username: String, employeename: String): Response<List<Employee>>
    suspend fun addJobTitle(username: String, employeename: String, jobTitle: String): Response<List<Employee>>
    suspend fun deleteJobTitle(username: String, employeename: String): Response<List<Employee>>
    suspend fun getAnnouncements(groupname: String): Response<List<Announcement>>
    suspend fun createAd(groupname: String, announcement: Announcement): Response<HttpResponse>
    suspend fun setEmotion(
        announcement: Announcement,
        emotion: String,
        username: String,
        groupname: String
    ): Response<List<Announcement>>

    suspend fun getTheEmployeesDepartment(groupname: String, username: String): Response<List<Employee>>
    suspend fun getDepartmentName(): Response<List<DepartmentName>>
    suspend fun getAnnouncement(id: Long): Response<Announcement>
}