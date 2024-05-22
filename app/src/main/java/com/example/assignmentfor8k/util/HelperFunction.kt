package com.example.assignmentfor8k.util

import android.graphics.Color
import android.text.Editable
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    fun checkEditableEmptyOrNull(text: Editable?):Boolean {

        if(text==null){
            return false
        }

        if(text.toString().isEmpty()){
            return false
        }

        return true
    }

    /**
     * Returns the n-th fibonacci number
     * They are defined like this:
     * fib(0) = 0
     * fib(1) = 1
     * fib(n) = fib(n-2) + fib(n-1)
     */
    fun fib(n: Int): Long {
        if(n == 0 || n == 1) {
            return n.toLong()
        }
        var a = 0L
        var b = 1L
        var c = 1L
        (1..n-2).forEach { i ->
            c = a + b
            a = b
            b = c
        }
        return c
    }

    /**
     * Checks if the braces are set correctly
     * e.g. "(a * b))" should return false
     */
    fun checkBraces(string: String): Boolean {
        return string.count { it == '(' } == string.count { it == ')' }
    }


}