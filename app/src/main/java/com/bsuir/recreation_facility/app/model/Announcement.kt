package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Announcement(
    val id: Long,
    val message: String,
    val creator: Employee,
    val emotions: List<Emotion>,
    val comments: List<Comment>,
    val views: List<Viewing>,
    val dateCreate: Date
)
