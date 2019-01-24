package com.rivia.software.kotlinmvvmcoroutines.utils


fun Double.parseAmountWithFeeAsDouble(fee: Double): Double{
    return this + (this*fee/100.0)
}

fun Double.withDecimals():String {
    return String.format("%.2f", this)
}

fun String.addCurrency(): String{
    return this+" €"
}