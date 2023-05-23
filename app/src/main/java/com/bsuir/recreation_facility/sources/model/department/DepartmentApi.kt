package com.bsuir.recreation_facility.sources.model.department

import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.DepartmentName
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import retrofit2.Response
import retrofit2.http.*

interface DepartmentApi {

    @GET("department/set")
    suspend fun registerAnEmployeeInTheDepartment(@Query("username") username: String,
                                                  @Query("employeename") employeename: String,
                                                  @Query("departmentName")  departmentName: String): Response<List<Employee>>

    @GET("department/delete")
    suspend fun deleteEmployeeFromDepartment(@Query("username") username: String,
                                             @Query("employeename") employeename: String): Response<List<Employee>>


    @GET("jobTitle/add")
    suspend fun addJobTitle(@Query("username") username: String,
                            @Query("employeename") employeename: String,
                            @Query("jobTitle")  jobTitle: String): Response<List<Employee>>

    @GET("jobTitle/delete")
    suspend fun deleteJobTitle(@Query("username") username: String,
                               @Query("employeename") employeename: String): Response<List<Employee>>

    @GET("department/announcements/{groupname}")
    suspend fun getAnnouncements(@Path("groupname") groupname: String): Response<List<Announcement>>

    @POST("department/create/announcement/{groupname}")
    suspend fun createAd(@Path("groupname") groupname: String,
                         @Body announcement: Announcement): Response<HttpResponse>

    @POST("department/{groupname}/create/emotion/{emotion}/{username}")
    suspend fun setEmotion(@Body announcement: Announcement,
                           @Path("emotion") emotion: String,
                           @Path("username") username: String,
                           @Path("groupname") groupname: String): Response<List<Announcement>>

    @GET("department/employees")
    suspend fun getTheEmployeesDepartment(@Query("groupname") groupname: String,
                                          @Query("username") username: String): Response<List<Employee>>

    @GET("department/departmentname")
    suspend fun getDepartmentName(): Response<List<DepartmentName>>

    @GET("department/announcement")
    suspend fun getAnnouncement(@Query("id") id: Long): Response<Announcement>


}