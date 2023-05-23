package com.bsuir.recreation_facility.sources.model.room

import com.bsuir.recreation_facility.app.model.Room
import com.bsuir.recreation_facility.sources.backend.BackendRetrofitSource
import com.bsuir.recreation_facility.sources.backend.RetrofitConfig
import kotlinx.coroutines.delay
import retrofit2.Response

class RetrofitRoomSource(
    config: RetrofitConfig
) : BackendRetrofitSource(config), RoomSource {

    private val roomApi = retrofit.create(RoomApi::class.java)

    override suspend fun getCorrespondence(username: String): Response<List<Room>> = wrapRetrofitExceptions {
        delay(1000)
        roomApi.getCorrespondence(username)
    }

}