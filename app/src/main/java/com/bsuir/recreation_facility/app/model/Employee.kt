package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Employee(
    var id: Long,
    var user: User,
    var jobTitle: JobTitle?,
    var groups: DepartmentName?,
    var role: String?,
    var authorities: Array<String>?,
    var stimulation: List<Stimulation>?,
    var friends: List<Employee>?
)
