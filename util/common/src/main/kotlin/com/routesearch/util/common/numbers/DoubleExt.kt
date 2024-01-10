package com.routesearch.util.common.numbers

import android.icu.text.DecimalFormat

fun Double.format(pattern: String): String = DecimalFormat(pattern).format(this)
