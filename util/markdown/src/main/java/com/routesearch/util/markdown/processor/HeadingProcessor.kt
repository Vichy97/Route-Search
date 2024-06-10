package com.routesearch.util.markdown.processor

import com.routesearch.util.markdown.MarkdownElement

class HeadingProcessor : MarkdownLineProcessor {
  override fun canProcessLine(line: String) = line.startsWith("#")

  override fun processLine(line: String): MarkdownElement {
    val level = line.takeWhile { it == '#' }.length
    val text = line.dropWhile { it == '#' || it == ' ' }
    return MarkdownElement.Heading(level, text)
  }
}
