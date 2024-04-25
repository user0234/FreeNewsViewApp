package com.example.assignmentfor8k.repository

import androidx.lifecycle.LiveData
import com.example.assignmentfor8k.database.chipsDataBase.CategoryChipStatus
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass
import com.example.assignmentfor8k.database.chipsDataBase.ChipsDao

class ChipRepository(private val chipDao: ChipsDao) : ChipRepositoryBlueprint {
    override suspend fun enableChip(chipItem: ChipDataClass) {
        chipDao.saveChip(chipItem)
    }

    override suspend fun disableChip(chipItem: ChipDataClass) {
        chipDao.deleteChip(chipItem)
    }

    override  fun getAllEnabledChips(): LiveData<List<ChipDataClass>?> {
        return chipDao.getEnabledChips(CategoryChipStatus.Enabled)
    }

    override fun getAllChips():LiveData<List<ChipDataClass>?> {
        return chipDao.getAllChips()
    }

    override suspend fun getItemFromId(id: Int): ChipDataClass? {
            return chipDao.getChipFromId(id)
    }

}