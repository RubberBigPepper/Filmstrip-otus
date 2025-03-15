package com.akmanaev.filmstrip.convertor

import com.tickaroo.tikxml.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter : TypeConverter<Date> {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    override fun read(value: String): Date {
        return formatter.parse(value)!!
    }

    override fun write(value: Date): String {
        return formatter.format(value)
    }
}