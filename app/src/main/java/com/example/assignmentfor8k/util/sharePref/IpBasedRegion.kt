package com.example.assignmentfor8k.util.sharePref

import android.content.Context

object IpBasedRegion {

    private const val TAG = "CurrentUserIp"


    fun getCountry(context: Context): String {
        val current = SharedPrefFunc.getSharedPrefString(
            context,
            TAG,
            "UserCountry",
            "IN"
        )
        // default is {us} as the api does not give result for code - {in}
        return "us"

    }

    fun updateCountry(context: Context, value: String) {
        SharedPrefFunc.putSharedPrefString(
            context,
            TAG,
            "UserCountry",
            value
        )
    }

}