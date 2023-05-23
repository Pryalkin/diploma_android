package com.bsuir.recreation_facility.app.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Room(
    val id: Long,
    val number: String,
    val community: List<Employee>,
    val senders: List<Sender>
)
