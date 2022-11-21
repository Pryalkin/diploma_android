package com.bsuir.recreation_facility.sources.backend

import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.employee.RetrofitEmployeeSource
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

}