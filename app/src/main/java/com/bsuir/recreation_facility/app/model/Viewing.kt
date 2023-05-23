package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Viewing(
    val id: Long,
    val employee: Employee,
    val viewingDate: Date
)
