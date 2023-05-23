package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DepartmentName(
    val id: Long,
    val name: String)
