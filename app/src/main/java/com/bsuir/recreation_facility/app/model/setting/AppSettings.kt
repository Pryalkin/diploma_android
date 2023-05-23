package com.bsuir.recreation_facility.app.model.setting

interface AppSettings {

    /**
     * Get auth token of the current logged-in user.
     */
    fun getCurrentToken(): String?

    /**
     * Set auth token of the logged-in user.
     */
    fun setCurrentToken(token: String?)

    fun getCurrentUsername(): String?

    fun setCurrentUsername(username: String?)

    fun setCurrentRole(role: String?)

    fun getCurrentRole(): String?

    fun setCurrentGroupname(group: String?)

    fun getCurrentGroupname(): String?

    fun setCurrentSumma(summa: Float?)

    fun getCurrentSumma(): Float?



}