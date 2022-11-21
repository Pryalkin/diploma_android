package com.bsuir.recreation_facility.app.model

data class Employee(
    var id: Long,
    var user: User?,
    var jobTitle: String,
    var role: String,
    var authorities: Array<String>?,
    var premium: Double
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (id != other.id) return false
        if (user != other.user) return false
        if (jobTitle != other.jobTitle) return false
        if (role != other.role) return false
        if (!authorities.contentEquals(other.authorities)) return false
        if (premium != other.premium) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + jobTitle.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + authorities.contentHashCode()
        result = 31 * result + premium.hashCode()
        return result
    }
}
