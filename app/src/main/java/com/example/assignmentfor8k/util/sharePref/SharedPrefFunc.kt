package com.example.assignmentfor8k.util.sharePref

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

    fun getSharedPrefString(
        context: Context,
        tag: String,
        value: String,
        defaultValue: String,
    ): String {
        val sharedPref = context.getSharedPreferences(tag, Context.MODE_PRIVATE)
        return sharedPref.getString(value, defaultValue) ?: defaultValue
    }

    fun putSharedPrefString(context: Context, tag: String, key: String, value: String) {
        val sharedPref = context.getSharedPreferences(tag, Context.MODE_PRIVATE)
        sharedPref.edit().putString(key, value).apply()
    }

}