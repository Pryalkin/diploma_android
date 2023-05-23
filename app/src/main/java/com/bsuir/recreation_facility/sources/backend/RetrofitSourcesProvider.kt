package com.bsuir.recreation_facility.sources.backend

import com.bsuir.recreation_facility.sources.model.department.DepartmentSource
import com.bsuir.recreation_facility.sources.model.department.RetrofitDepartmentSource
import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.employee.RetrofitEmployeeSource
import com.bsuir.recreation_facility.sources.model.room.RetrofitRoomSource
import com.bsuir.recreation_facility.sources.model.room.RoomSource
import com.bsuir.recreation_facility.sources.model.user.RetrofitUserSource
import com.bsuir.recreation_facility.sources.model.user.UserSource

class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider{

    override fun getUserSource(): UserSource {
        return RetrofitUserSource(config)
    }

    override fun getEmployeeSource(): EmployeeSource {
        return RetrofitEmployeeSource(config)
    }

    override fun getDepartmentSource(): DepartmentSource {
        return RetrofitDepartmentSource(config)
    }

    override fun getRoomSource(): RoomSource {
        return RetrofitRoomSource(config)
    }

}