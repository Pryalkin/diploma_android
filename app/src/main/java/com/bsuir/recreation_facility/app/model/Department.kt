package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Department(
    val id: Long,
    val name: DepartmentName,
    val administration: List<Employee>,
    val community: List<Employee>,
    val announcements: List<Announcement>
)
