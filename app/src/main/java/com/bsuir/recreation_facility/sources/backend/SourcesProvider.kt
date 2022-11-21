package com.bsuir.recreation_facility.sources.backend

import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.user.UserSource

interface SourcesProvider {

    fun getUserSource(): UserSource
    fun getEmployeeSource(): EmployeeSource
}