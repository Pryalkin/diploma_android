package com.bsuir.recreation_facility.sources.backend

import com.bsuir.recreation_facility.sources.model.department.DepartmentSource
import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.room.RoomSource
import com.bsuir.recreation_facility.sources.model.user.UserSource

interface SourcesProvider {

    fun getUserSource(): UserSource
    fun getEmployeeSource(): EmployeeSource
    fun getDepartmentSource(): DepartmentSource
    fun getRoomSource(): RoomSource
}