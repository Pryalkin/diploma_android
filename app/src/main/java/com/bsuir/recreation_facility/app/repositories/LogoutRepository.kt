package com.bsuir.recreation_facility.app.repositories

import com.bsuir.recreation_facility.app.model.setting.AppSettings
import com.bsuir.recreation_facility.sources.model.user.UserSource

class LogoutRepository (
    private val appSettings: AppSettings
) {

   fun logout() {
        appSettings.setCurrentUsername("")
        appSettings.setCurrentToken("")
        appSettings.setCurrentRole("")
    }

}