package com.example.assignmentfor8k.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.assignmentfor8k.retrofit.newsApi.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters  {
    private val gson = Gson()

    @TypeConverter
    fun fromSource(value: Source): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSource(value: String): Source {
        return gson.fromJson(value, object : TypeToken<Source>() {
        }.type)
    }
}