package com.bsuir.recreation_facility.sources.utils

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter: JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(SERVER_FORMAT, Locale("Europe/Minsk"))

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            synchronized(dateFormat) {
                dateFormat.parse(dateAsString)
            }
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            synchronized(dateFormat) {
                writer.value(value.time)
            }
        }
    }

    companion object {
        const val SERVER_FORMAT = ("MM-dd-yyyy hh:mm:ss") // define your server format here
    }
}