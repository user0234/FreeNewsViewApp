package com.example.assignmentfor8k.database.chipsDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "chips_table")
data class ChipDataClass(
    @PrimaryKey
    val id:Int,
    val value:String,
    val status:CategoryChipStatus
)


enum class CategoryChipStatus {
    Enabled,
    Disabled
}