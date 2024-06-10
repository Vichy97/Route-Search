package com.routesearch.util.markdown

import com.routesearch.util.markdown.processor.MarkdownLineProcessor

class MarkdownParser(
  private val lineProcessors: List<MarkdownLineProcessor>,
) {

  fun parse(
    lines: List<String>,
  ): List<MarkdownElement> {
    val content = mutableListOf<MarkdownElement>()
    var body = ""
    lines.forEach { line ->
      val processor = lineProcessors.find { it.canProcessLine(line) }
      if (processor != null) {
        if (body.isNotEmpty()) {
          content.add(MarkdownElement.Body(body))
          body = ""
        }
        content.add(processor.processLine(line))
      } else if (line.isBlank()) {
        body += "\n"
      } else {
        body += line
      }
    }
    content.add(MarkdownElement.Body(body))

    return content
  }
}
