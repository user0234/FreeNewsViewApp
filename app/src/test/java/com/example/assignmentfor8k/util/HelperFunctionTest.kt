package com.example.assignmentfor8k.util

import android.text.Editable
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HelperFunctionTest {

    @Test
    fun `editable is empty return false` ()  {
        val value = Editable.Factory.getInstance().newEditable("")
       val result:Boolean =  HelperFunction.checkEditableEmptyOrNull(
           value
       )
        assertThat(result).isFalse()
    }
}