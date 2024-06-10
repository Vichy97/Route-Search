package com.routesearch.util.markdown

sealed interface MarkdownElement {

  data class Heading(
    val level: Int,
    val text: String,
  ) : MarkdownElement

  data class Body(
    val text: String,
  ) : MarkdownElement

  data class ListItem(
    val text: String,
  ) : MarkdownElement
}
