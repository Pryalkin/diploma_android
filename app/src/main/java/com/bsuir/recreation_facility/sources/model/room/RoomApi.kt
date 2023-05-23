package com.bsuir.recreation_facility.sources.model.room

import com.bsuir.recreation_facility.app.model.Room
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RoomApi {

    @GET("room/getAll")
    suspend fun getCorrespondence(@Query("username") username: String): Response<List<Room>>

}