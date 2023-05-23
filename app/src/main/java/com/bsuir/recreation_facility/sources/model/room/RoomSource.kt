package com.bsuir.recreation_facility.sources.model.room

import com.bsuir.recreation_facility.app.model.Room
import retrofit2.Response

interface RoomSource {
    suspend fun getCorrespondence(username: String): Response<List<Room>>
}