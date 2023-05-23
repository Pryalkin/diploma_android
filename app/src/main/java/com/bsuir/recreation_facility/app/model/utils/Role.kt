package com.bsuir.recreation_facility.app.model.utils

import com.bsuir.recreation_facility.app.model.utils.Authority.ADMIN_AUTHORITIES
import com.bsuir.recreation_facility.app.model.utils.Authority.MANAGER_AUTHORITIES
import com.bsuir.recreation_facility.app.model.utils.Authority.SUPER_ADMIN_AUTHORITIES
import com.bsuir.recreation_facility.app.model.utils.Authority.USER_AUTHORITIES

enum class Role(vararg authorities: String) {
    ROLE_USER(*USER_AUTHORITIES),
    ROLE_MANAGER(*MANAGER_AUTHORITIES),
    ROLE_ADMIN(*ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(*SUPER_ADMIN_AUTHORITIES);

    val authorities: Array<String>

    init {
        this.authorities = authorities as Array<String>
    }
}