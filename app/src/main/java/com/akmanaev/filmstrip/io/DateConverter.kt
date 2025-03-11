package com.akmanaev.filmstrip.io

import com.tickaroo.tikxml.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class DateConverter : TypeConverter<Date> {
    private val formatter: SimpleDateFormat =
        SimpleDateFormat("yyyy-MM-dd HH:mm:SS") // SimpleDateFormat is not thread safe!

    override fun read(value: String): Date {
        return formatter.parse(value)
    }

    override fun write(value: Date): String {
        return formatter.format(value)
    }
}