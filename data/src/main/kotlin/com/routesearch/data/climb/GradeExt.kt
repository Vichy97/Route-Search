package com.routesearch.data.climb

fun Grades.getDisplayName(type: Type) = vScale.takeIf { type == Type.BOULDERING } ?: yds
