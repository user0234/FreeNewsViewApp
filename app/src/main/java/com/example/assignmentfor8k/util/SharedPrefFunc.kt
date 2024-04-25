package com.example.assignmentfor8k.util

import android.content.Context

object SharedPrefFunc {

    fun getSharedPrefBoolean(
        context: Context,
        tag: String,
        key: String,
        defaultValue: Boolean,
    ): Boolean {
        val sharedPref = context.getSharedPreferences(tag, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(key, defaultValue)
    }


    fun putSharedPrefBoolean(context: Context, tag: String, key: String, value: Boolean) {
        val sharedPref = context.getSharedPreferences(tag, Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean(key, value).apply()
    }

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