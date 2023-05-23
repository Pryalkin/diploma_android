package com.bsuir.recreation_facility.app.screens.app

import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User

interface Navigator {
    fun showInfo(user: User)
    fun showAnnouncement(announcement: Announcement)
    fun goBack()
    fun toast(messageRes: String)
    fun showEmployee(employee: Employee)
    fun showEmp(employee: Employee)
    fun updateEmployee(username: String, navigator: Navigator)
}