package com.example.assignmentfor8k.util

import android.graphics.Color
import android.text.style.TtsSpan.DateBuilder
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

object HelperFunction {

    fun getCurrentDate():String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.now().format(formatter).toString()
    }

     fun showSnackBar(message:String,view: View){
        val snackBar = Snackbar
            .make(view,
                "$message", Snackbar.LENGTH_LONG)
        snackBar.setTextColor(Color.RED)
        snackBar.show()

    }
}