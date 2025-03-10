package com.example.assignmentfor8k.util

import com.example.assignmentfor8k.database.chipsDataBase.CategoryChipStatus
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass

object Constants {
    const val BASE_URL_IP_COUNTRY = "https://api.country.is"
    const val NEWS_API_BASEURL = "https://newsapi.org/v2/"
    const val NEWS_API_KEY = "d91dd07987d04818b102b28b4e43aed3"
    const val QUERY_PAGE_SIZE = 20


    fun getAllTheChips(): List<ChipDataClass> {
        return chipsItemList
    }

    private val chipAllItem: ChipDataClass = ChipDataClass(0, "All", CategoryChipStatus.Enabled)
    private val chipBusiness: ChipDataClass = ChipDataClass(1, "business", CategoryChipStatus.Enabled)
    private val chipEntertainment: ChipDataClass = ChipDataClass(2, "entertainment", CategoryChipStatus.Enabled)
    private val chipGeneral: ChipDataClass = ChipDataClass(3, "general", CategoryChipStatus.Enabled)
    private val chipHealth: ChipDataClass = ChipDataClass(4, "health", CategoryChipStatus.Enabled)
    private val chipScience: ChipDataClass = ChipDataClass(5, "science", CategoryChipStatus.Enabled)
    private val chipSports: ChipDataClass = ChipDataClass(6, "sports", CategoryChipStatus.Enabled)
    private val chipTechnology: ChipDataClass = ChipDataClass(7, "technology", CategoryChipStatus.Enabled)

    private val chipsItemList: List<ChipDataClass> =
        listOf(
            chipAllItem,
            chipBusiness,
            chipEntertainment,
            chipGeneral,
            chipHealth,
            chipScience,
            chipSports,
            chipTechnology
        )

}