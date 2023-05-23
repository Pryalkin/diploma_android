package com.bsuir.recreation_facility.app.model.utils

object Authority {
    val USER_AUTHORITIES =
        arrayOf("user:read", "user:create", "user:edit", "user:update", "user:change")
    val MANAGER_AUTHORITIES = arrayOf<String>()
    val ADMIN_AUTHORITIES =
        arrayOf("user:read", "user:create", "user:edit", "user:update", "user:change",
            "user:block", "user:delete_message" //            "user:correct"
        )
    val SUPER_ADMIN_AUTHORITIES =
        arrayOf("user:read", "user:create", "user:edit", "user:update", "user:change",
            "user:block", "user:delete_message", "user:appoint", "user:access", "user:delete_user")
}