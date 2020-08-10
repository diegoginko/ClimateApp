package com.diegoginko.climapp.utilities

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat


@InverseMethod("stringToDouble")
fun doubleToString(double: Double): String? {
    return if (double == 0.0) "0.00" else double.toString()
}

fun stringToDouble(string: String): Double {
    return if (string.isEmpty()) 0.0 else string.toDouble()
}

fun dtToDow(systemTime: Long): String {
    return SimpleDateFormat("EEEE")
        .format(systemTime).toString().trim()
}

fun iconToUri(icon: String): String{
    return "openweathermap.org/img/wn/"+icon.trim()+"@2x.png"
}