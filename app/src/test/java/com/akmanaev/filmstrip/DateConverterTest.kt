package com.akmanaev.filmstrip

import com.akmanaev.filmstrip.convertor.DateConverter
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class DateConverterTest{
    @Test
    fun parseStringToDate(){
        val converter = DateConverter()

        val calendar = Calendar.getInstance()
        calendar.time = converter.read("2020-07-14 15:52:46")
        assertEquals(2020, calendar[Calendar.YEAR])
        assertEquals(7 - 1, calendar[Calendar.MONTH])
        assertEquals(14, calendar[Calendar.DAY_OF_MONTH])

        assertEquals(15, calendar[Calendar.HOUR_OF_DAY])
        assertEquals(52, calendar[Calendar.MINUTE])
        assertEquals(46, calendar[Calendar.SECOND])
    }

    @Test
    fun parseDateToString(){
        val converter = DateConverter()
        val origin = "2024-12-23 17:32:26"
        val text = converter.write(converter.read(origin))
        assertEquals(origin, text)
    }

    @Test
    fun parseInvalidFormat(){
        val converter = DateConverter()
        val origin = "2024-12-23"
        assertThrows(Exception::class.java){
            converter.read(origin)
        }
    }
}