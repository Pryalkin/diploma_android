package com.bsuir.recreation_facility

import android.content.Context
import androidx.fragment.app.Fragment
import com.bsuir.recreation_facility.app.model.setting.AppSettings
import com.bsuir.recreation_facility.app.model.setting.SharedPreferencesAppSettings
import com.bsuir.recreation_facility.app.repositories.*
import com.bsuir.recreation_facility.app.screens.app.Navigator
import com.bsuir.recreation_facility.sources.SourceProviderHolder
import com.bsuir.recreation_facility.sources.backend.SourcesProvider
import com.bsuir.recreation_facility.sources.model.department.DepartmentSource
import com.bsuir.recreation_facility.sources.model.employee.EmployeeSource
import com.bsuir.recreation_facility.sources.model.room.RoomSource
import com.bsuir.recreation_facility.sources.model.user.UserSource

object Singletons {

    private lateinit var appContext: Context

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    // source

    private val userSource: UserSource by lazy {
        sourcesProvider.getUserSource()
    }

    private val employeeSource: EmployeeSource by lazy {
        sourcesProvider.getEmployeeSource()
    }

    private val departmentSource: DepartmentSource by lazy {
        sourcesProvider.getDepartmentSource()
    }

    private val roomSource: RoomSource by lazy {
        sourcesProvider.getRoomSource()
    }

    // repository

    val userRepository: UserRepository by lazy {
        UserRepository(
            userSource = userSource
        )
    }

    val employeeRepository: EmployeeRepository by lazy {
        EmployeeRepository(
            employeeSource = employeeSource,
            appSettings = appSettings
        )
    }

    val logoutRepository: LogoutRepository by lazy {
        LogoutRepository(
            appSettings = appSettings
        )
    }

    val cabinetRepository: CabinetRepository by lazy {
        CabinetRepository(
            employeeSource = employeeSource,
            departmentSource = departmentSource,
            roomSource = roomSource,
            appSettings = appSettings
        )
    }

    val departmentRepository: DepartmentRepository by lazy {
        DepartmentRepository(
            departmentSource = departmentSource,
            appSettings = appSettings
        )
    }

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }

    fun Fragment.navigator() = requireActivity() as Navigator
}