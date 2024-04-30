package com.example.assignmentfor8k.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass
import com.example.assignmentfor8k.database.chipsDataBase.ChipsDao
import com.example.assignmentfor8k.database.newsDataBase.NewsDao
import com.example.assignmentfor8k.retrofit.newsApi.model.Article

// main app database with 2 tables
@Database(
    entities = [Article::class,ChipDataClass::class],
    version = 1, exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun chipsDao(): ChipsDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            val INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "app_dataBase"
            ).build()
            instance = INSTANCE

            instance
        }
    }


}