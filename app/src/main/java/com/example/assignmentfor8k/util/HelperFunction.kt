package com.example.assignmentfor8k.util

import android.content.Context
import android.graphics.Color
import android.os.Environment
import android.text.style.TtsSpan.DateBuilder
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.io.File
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

    fun createFileForSaving(fileName: String,context: Context): File {
        val directoryNew = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val directoryNew2 = File(directoryNew, "DesktopBrowser")
        val directory = File(directoryNew2, "Barcode")

        if (!directory.exists()) {
            directory.mkdirs() // Create download/desktopBrowser directory if it doesn't exist
        }
        return File("$directory", fileName)
    }

}