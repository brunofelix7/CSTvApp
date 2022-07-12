package me.brunofelix.cstvapp.util

import timber.log.Timber
import java.sql.Time
import java.sql.Timestamp
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

const val PATTERN_MM_DD_YYYY_K_MM_A = "MM-dd-yyyy k:mm a"
const val PATTERN_MM_DD_YYYY_HH_MM = "MM-dd-yyyy HH:mm"
const val PATTERN_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm"
const val PATTERN_H_MM_A = "h:mm a"
const val PATTERN_YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val PATTERN_EEE_HH_MM = "EEE, HH:mm"

fun convertDate(matchDate: String?, pattern: String) : String {
    return try {
        val sdf = SimpleDateFormat(PATTERN_YYYY_MM_DD_T_HH_MM_SS_Z, Locale.getDefault())
        val sdf2 = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")

        val tz = TimeZone.getDefault()
        sdf2.timeZone = TimeZone.getTimeZone(tz.id)

        val date = sdf.parse(matchDate)
        sdf2.format(date)
    } catch (e: Exception) {
        Timber.e(e)
        "Undefined"
    }
}

fun convertToTimestamp(date: String, pattern: String): Long {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        val sdf2 = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")

        val tz = TimeZone.getDefault()
        sdf2.timeZone = TimeZone.getTimeZone(tz.id)

        val parsedDate = sdf.parse(date) ?: return 0
        Timestamp(parsedDate.time).time
    } catch (e: Exception) {
        Timber.e(e)
        System.currentTimeMillis()
    }
}

fun convertFromTimestamp(timeStamp: Long, pattern: String): String {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(Timestamp(timeStamp).time)
        sdf.format(date)
    } catch (e: Exception) {
        Timber.e(e)
        "Undefined"
    }
}

fun getDuration(timeStamp: Long): Long {
    return try {
        val diff = abs(timeStamp - System.currentTimeMillis())
        TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
    } catch (e: Exception) {
        Timber.e(e)
        System.currentTimeMillis()
    }
}

fun getDiffBetweenDates(timeStamp: Long): Long {
    return try {
        Timestamp(timeStamp).time.minus(System.currentTimeMillis())
    } catch (e: Exception) {
        Timber.e(e)
        0
    }
}

fun getTime(hr: Int, min: Int): String? {
    return try {
        val tme = Time(hr, min, 0)
        val formatter: Format
        formatter = SimpleDateFormat(PATTERN_H_MM_A, Locale.getDefault())
        formatter.format(tme)
    } catch (e: Exception) {
        Timber.e(e)
        "Undefined"
    }
}
