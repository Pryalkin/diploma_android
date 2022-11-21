package com.bsuir.recreation_facility.app.model

import java.util.*

data class User (
    val id: Long,
    val name: String,
    val surname: String,
    val patronymic: String,
    val dateOfBirth: Date,
    val phone: String,
    val email: String,
    val username: String,
    val password: String,
    val lastLoginDateDisplay: Date,
    val lastLoginDate: Date,
    val confirmation: Boolean,
    val isNotLocked: Boolean,
    val isActive: Boolean
)
