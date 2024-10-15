package com.example.assignmentfor8k.util.sharePref

import android.content.Context
import com.example.assignmentfor8k.util.sharePref.SharedPrefFunc.getSharedPrefBoolean
import com.example.assignmentfor8k.util.sharePref.SharedPrefFunc.putSharedPrefBoolean

object ChipData {

    // chips tag
    val TAG = "chipsTag"

    fun getChipDataBase(context: Context) :Boolean {
        return  getSharedPrefBoolean(
            context,
            TAG,
            "chipDataBaseSetup",
            true
        )
    }

    fun updateChipDataBase(context: Context, value: Boolean) {
        putSharedPrefBoolean(
            context,
            TAG,
            "chipDataBaseSetup",
            value
        )
    }

}