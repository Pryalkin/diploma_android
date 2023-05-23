package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Emotion(
    val id: Long,
    val emotion: String,
    val creator: Employee,
    val dateCreate: Date
)
