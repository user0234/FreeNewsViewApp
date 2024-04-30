package com.example.assignmentfor8k.repository

import androidx.lifecycle.LiveData
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass

/**
 * blue print class for the repository to keep it clean
 */

interface ChipRepositoryBlueprint  {

    suspend fun enableChip(chipItem: ChipDataClass)

    suspend fun disableChip(chipItem: ChipDataClass)

     fun getAllEnabledChips(): LiveData<List<ChipDataClass>?>

    fun getAllChips():LiveData<List<ChipDataClass>?>

    suspend fun getItemFromId(i: Int): ChipDataClass?
}