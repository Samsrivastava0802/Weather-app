package com.rsstudio.weather.util

import android.content.Context

object GetTimeAgo {
    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS
    fun getTimeAgo(time: Long, ctx: Context?): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }
        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> {
                "Just Updated"
            }
            diff < 2 * MINUTE_MILLIS -> {
                "Updated" + " " +
                        "a min ago"
            }
            diff < 50 * MINUTE_MILLIS -> {
                "Updated" + " " +
                        (diff / MINUTE_MILLIS).toString() + " mins ago"
            }
            diff < 90 * MINUTE_MILLIS -> {
                "Updated" + " " +
                        "an hour ago"
            }
            diff < 24 * HOUR_MILLIS -> {
                "Updated" + " " +
                        (diff / HOUR_MILLIS).toString() + " hours ago"
            }
            diff < 48 * HOUR_MILLIS -> {
                "Updated" + " " +
                        "Yesterday"
            }
            else -> {
                "Updated" + " " +
                        (diff / DAY_MILLIS).toString() + " days ago"
            }
        }
    }
}
