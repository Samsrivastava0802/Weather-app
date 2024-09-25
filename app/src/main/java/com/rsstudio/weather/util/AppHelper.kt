package com.rsstudio.weather.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class AppHelper {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun getWeekDayName(s: String?): String? {
            val dtfInput: DateTimeFormatter = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH)
         var temp =  LocalDate.parse(s, dtfInput).dayOfWeek
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH)

            var answer = ""

            for (i in 0..2) {
                answer += temp[i]
            }
            return answer
        }

        fun getPatternOfDate(date: String): String {
            var format = date.split("-").toTypedArray()
            return format[2] + "/" + format[1]
        }

        fun getPatternOfHour(date: String): String {
            val temp = date.split("-").toTypedArray()
            val dd = temp[2]

            var answer = ""

            for (i in 3 until dd.length) {
                answer += dd[i]
            }
            return answer
        }
        fun compareForMyHourOFTheDay(timeWithCheck:String,actualTime:String): Boolean {

            val temp = timeWithCheck.split("-").toTypedArray()
            val dd = temp[2]

            var answer = ""

            for (i in 3..4) {
                answer += dd[i]
            }

            if (answer == actualTime){
                return true
            }
            return false
        }
    }
}