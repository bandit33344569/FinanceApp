package com.abrosimov.utils

import com.abrosimov.utils.dateutils.DateUtils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun data_isCorrect(){
        val time = "12:45"
        val date = "2025-07-11"
        val result = DateUtils.combineDateAndTimeToIso(date,time)
        print(result)
    }
}