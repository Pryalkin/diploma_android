package com.bsuir.recreation_facility

import android.content.Context
import com.bsuir.recreation_facility.app.repositories.EmployeeRepository
import com.bsuir.recreation_facility.app.repositories.UserRepository
import com.bsuir.recreation_facility.sources.SourceProviderHolder
import com.bsuir.recreation_facility.sources.backend.SourcesProvider
import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.user.UserSource

object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    // source

    private val userSource: UserSource by lazy {
        sourcesProvider.getUserSource()
    }

    private val employeeSource: EmployeeSource by lazy {
        sourcesProvider.getEmployeeSource()
    }

    // repository

    val userRepository: UserRepository by lazy {
        UserRepository(
            userSource = userSource
        )
    }

    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepository(
            employeeSource = employeeSource
        )
    }

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}