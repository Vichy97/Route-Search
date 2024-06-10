package com.routesearch.ui.common.compose

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

inline fun <R : Any> AnnotatedString.Builder.bold(
  block: AnnotatedString.Builder.() -> R,
): R = withStyle(
  SpanStyle(fontWeight = FontWeight.Bold),
) { block() }

inline fun <R : Any> AnnotatedString.Builder.underline(
  block: AnnotatedString.Builder.() -> R,
): R = withStyle(
  SpanStyle(textDecoration = TextDecoration.Underline),
) { block() }

inline fun <R : Any> AnnotatedString.Builder.annotation(
  annotation: String,
  block: AnnotatedString.Builder.() -> R,
): R {
  val index = pushStringAnnotation(
    tag = annotation,
    annotation = annotation,
  )
  return try {
    block(this)
  } finally {
    pop(index)
  }
}

inline fun <R : Any> AnnotatedString.Builder.annotation(
  tag: String,
  annotation: String,
  block: AnnotatedString.Builder.() -> R,
): R {
  val index = pushStringAnnotation(
    tag = tag,
    annotation = annotation,
  )
  return try {
    block(this)
  } finally {
    pop(index)
  }
}

fun AnnotatedString.isAnnotatedAtIndex(
  index: Int,
  annotation: String,
) = getStringAnnotations(start = index, end = index)
  .map { it.item }
  .contains(annotation)

fun AnnotatedString.getAnnotationAt(
  index: Int,
) = getStringAnnotations(start = index, end = index)
  .map { it.item }
  .firstOrNull()
