package com.bsuir.recreation_facility.sources.model.department

import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.DepartmentName
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.sources.backend.BackendRetrofitSource
import com.bsuir.recreation_facility.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitDepartmentSource (
    config: RetrofitConfig
) : BackendRetrofitSource(config), DepartmentSource {

    private val departmentApi = retrofit.create(DepartmentApi::class.java)

    override suspend fun registerAnEmployeeInTheDepartment(
        username: String,
        employeename: String,
        departmentName: String,
    ): Response<List<Employee>> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.registerAnEmployeeInTheDepartment(username, employeename, departmentName)
    }

    override suspend fun deleteEmployeeFromDepartment(username: String, employeename: String): Response<List<Employee>> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.deleteEmployeeFromDepartment(username, employeename)
    }

    override suspend fun addJobTitle(username: String, employeename: String, jobTitle: String): Response<List<Employee>> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.addJobTitle(username, employeename, jobTitle)
    }

    override suspend fun deleteJobTitle(
        username: String,
        employeename: String,
    ): Response<List<Employee>>  = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.deleteJobTitle(username = username, employeename = employeename)
    }

    override suspend fun getAnnouncements(groupname: String): Response<List<Announcement>> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.getAnnouncements(groupname)
    }

    override suspend fun createAd(
        groupname: String,
        announcement: Announcement,
    ): Response<HttpResponse> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.createAd(groupname, announcement)
    }

    override suspend fun setEmotion(
        announcement: Announcement,
        emotion: String,
        username: String,
        groupname: String
    ): Response<List<Announcement>> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.setEmotion(announcement, emotion, username, groupname)
    }

    override suspend fun getTheEmployeesDepartment(groupname: String, username: String): Response<List<Employee>> = wrapRetrofitExceptions  {
        delay(1000)
        departmentApi.getTheEmployeesDepartment(groupname, username)
    }

    override suspend fun getDepartmentName(): Response<List<DepartmentName>> = wrapRetrofitExceptions {
        delay(1000)
        departmentApi.getDepartmentName()
    }

    override suspend fun getAnnouncement(id: Long): Response<Announcement> = wrapRetrofitExceptions  {
        delay(1000)
        departmentApi.getAnnouncement(id)
    }
}