package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Comment(
    val id: Long,
    val creator: Employee,
    val message: String,
    val emotions: List<Emotion>,
    val dateCreate: Date
)
