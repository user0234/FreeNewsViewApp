package com.example.assignmentfor8k.database.chipsDataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChipsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChip(chipItem: ChipDataClass)

    @Delete
    suspend fun deleteChip(chipItem: ChipDataClass)

    @Query("Select * From chips_table Where status =:status ")
    fun getEnabledChips(status:CategoryChipStatus): LiveData<List<ChipDataClass>?>

    @Query("Select * From chips_table")
    fun getAllChips(): LiveData<List<ChipDataClass>?>

    @Query("Select * From chips_table Where id =:id")
    suspend fun getChipFromId(id: Int): ChipDataClass?

}