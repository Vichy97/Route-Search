package com.routesearch.util.markdown.processor

import com.routesearch.util.markdown.MarkdownElement

interface MarkdownLineProcessor {
  fun canProcessLine(line: String): Boolean
  fun processLine(line: String): MarkdownElement
}
