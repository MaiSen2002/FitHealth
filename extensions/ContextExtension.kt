package com.bloodpressure.bloodtracker.bptracker.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.text.Editable
import android.view.View
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.hasNetworkConnection(): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    val cm =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.allNetworkInfo
    for (ni in netInfo) {
        if (ni.typeName.equals("WIFI", ignoreCase = true))
            if (ni.isConnected) haveConnectedWifi = true
        if (ni.typeName.equals("MOBILE", ignoreCase = true))
            if (ni.isConnected) haveConnectedMobile = true
    }
    return haveConnectedWifi || haveConnectedMobile
}

fun Context.setLocale(language: String?) {
    val configuration = resources.configuration
    val locale = if (language.isNullOrEmpty()) {
        Locale.getDefault()
    } else {
        Locale(language)
    }
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    createConfigurationContext(configuration)
}

fun Long.toDateString(): String {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(this)
}

fun toTimeString(hour: Int, minute: Int): String {
    val hourText = if (hour < 10) "0${hour}" else "$hour"
    val minuteText = if (minute < 10) "0${minute}" else "$minute"
    return "$hourText:$minuteText"
}

fun Long.convertDateToString(format: String): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(date)
}

fun getColor(sugar: Double): String {
    return when (sugar) {
        in 0.0..3.9 -> "#41ACE9"
        in 4.0..5.4 -> "#00C57E"
        in 5.5..6.9 -> "#E9D841"
        else -> "#FB5555"
    }
}

// Convert Int -> Date
fun convertToDate(year: Int, month: Int, day: Int): Date? {
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return calendar.time
}

// Convert Int -> Calendar
fun convertToCalendarFromDate(year: Int, month: Int, day: Int): Calendar? {
    val calendar = Calendar.getInstance()
    calendar[year, month] = day
    return calendar
}

// Convert Time -> Calendar
fun convertToCalendarFromTime(hour: Int, minute: Int): Calendar? {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = hour
    calendar[Calendar.MINUTE] = minute
    calendar[Calendar.SECOND] = 0 // default:  0
    return calendar
}
// Convert String to Editable
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


