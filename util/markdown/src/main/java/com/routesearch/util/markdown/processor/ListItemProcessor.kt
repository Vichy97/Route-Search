package com.routesearch.util.markdown.processor

import com.routesearch.util.markdown.MarkdownElement

class ListItemProcessor : MarkdownLineProcessor {
  override fun canProcessLine(line: String): Boolean {
    val indicators = listOf("- ", "* ", "+ ")
    return indicators.any { line.startsWith(it) }
  }

  override fun processLine(line: String): MarkdownElement {
    val text = line.drop(2)
    return MarkdownElement.ListItem(text)
  }
}
