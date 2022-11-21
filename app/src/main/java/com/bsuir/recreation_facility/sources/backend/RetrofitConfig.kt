package com.bsuir.recreation_facility.sources.backend

import com.squareup.moshi.Moshi
import retrofit2.Retrofit

class RetrofitConfig(
    val retrofit: Retrofit,
    val moshi: Moshi
)