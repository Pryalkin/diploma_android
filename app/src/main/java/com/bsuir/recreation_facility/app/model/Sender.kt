package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Sender(
    val id: Long,
    val employee: Employee,
    val message: String,
    val dateCreate: Date
)
