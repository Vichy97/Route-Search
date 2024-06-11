package com.routesearch.features.common.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.compose.url
import com.routesearch.ui.common.theme.RouteSearchTheme
import com.routesearch.util.markdown.MarkdownElement
import com.routesearch.util.markdown.MarkdownParser
import com.routesearch.util.markdown.processor.HeadingProcessor
import com.routesearch.util.markdown.processor.ListItemProcessor

private const val BOLD_ITALIC_ANNOTATION_LENGTH = 3
private const val BOLD_ANNOTATION_LENGTH = 2

@Composable
internal fun MarkdownView(
  text: String,
) = Column {
  val lineProcessors = remember { listOf(HeadingProcessor(), ListItemProcessor()) }
  val markdownParser = remember { MarkdownParser(lineProcessors) }
  val markdownElements = remember(text) { markdownParser.parse(text.lines()) }

  markdownElements.forEach { element ->
    when (element) {
      is MarkdownElement.Heading -> {
        val style = when (element.level) {
          1 -> MaterialTheme.typography.titleLarge
          2 -> MaterialTheme.typography.titleMedium
          else -> MaterialTheme.typography.titleSmall
        }
        Text(
          text = element.text,
          style = style,
        )
      }

      is MarkdownElement.ListItem -> {
        MarkdownText(
          text = "• ${element.text}",
          style = MaterialTheme.typography.bodySmall,
        )
      }

      is MarkdownElement.Body -> {
        MarkdownText(
          text = element.text,
          style = MaterialTheme.typography.bodySmall,
        )
      }
    }
  }
}

@Composable
private fun MarkdownText(
  text: String,
  style: TextStyle,
) {
  var annotatedString = boldItalicAnnotation(AnnotatedString(text))
  annotatedString = boldAnnotation(annotatedString)
  annotatedString = italicAnnotation(annotatedString)
  annotatedString = linkAnnotation(annotatedString)

  Text(
    text = annotatedString,
    style = style,
  )
}

private fun linkAnnotation(text: AnnotatedString): AnnotatedString {
  val linkRegex = Regex("""\[([^\[]+)]\(([^)]+)\)""")

  return buildAnnotatedString {
    var currentIndex = 0
    linkRegex.findAll(text).forEach { matchResult ->
      val (linkText, url) = matchResult.destructured
      val matchStart = matchResult.range.first
      val matchEnd = matchResult.range.last + 1

      if (currentIndex < matchStart) {
        append(text.subSequence(currentIndex, matchStart))
      }

      underline {
        url(url) {
          append(linkText)
        }
      }

      currentIndex = matchEnd
    }

    if (currentIndex < text.length) {
      append(text.subSequence(currentIndex, text.length))
    }
  }
}

private fun boldItalicAnnotation(text: AnnotatedString): AnnotatedString {
  val boldItalics = Regex("""(\*\*\*|___)(.+?)\1""")
    .findAll(text)

  var currentIndex = 0
  val annotatedString = buildAnnotatedString {
    boldItalics.forEach { matchResult ->
      val match = matchResult.value
      val prefix = text.subSequence(currentIndex, matchResult.range.first)
      append(prefix)
      currentIndex = matchResult.range.last + 1

      val boldItalicText = match.subSequence(
        BOLD_ITALIC_ANNOTATION_LENGTH,
        match.length - BOLD_ITALIC_ANNOTATION_LENGTH,
      )
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
        append(boldItalicText)
      }
    }
    append(text.substring(currentIndex))
  }
  return annotatedString
}

private fun boldAnnotation(text: AnnotatedString): AnnotatedString {
  val bold = Regex("""(\*\*|__)(.+?)\1""")
    .findAll(text)

  var currentIndex = 0
  val annotatedString = buildAnnotatedString {
    bold.forEach { matchResult ->
      val match = matchResult.value
      val prefix = text.subSequence(currentIndex, matchResult.range.first)
      append(prefix)
      currentIndex = matchResult.range.last + 1

      val boldText = match.subSequence(BOLD_ANNOTATION_LENGTH, match.length - BOLD_ANNOTATION_LENGTH)
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
        append(boldText)
      }
    }
    append(text.subSequence(currentIndex, text.length))
  }
  return annotatedString
}

private fun italicAnnotation(text: AnnotatedString): AnnotatedString {
  val italic = Regex("""([*_])(.+?)\1""")
    .findAll(text)

  var currentIndex = 0
  val annotatedString = buildAnnotatedString {
    italic.forEach { matchResult ->
      val match = matchResult.value
      val prefix = text.subSequence(currentIndex, matchResult.range.first)
      append(prefix)
      currentIndex = matchResult.range.last + 1

      val italicText = match.subSequence(1, match.length - 1)
      withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
        append(italicText)
      }
    }
    append(text.subSequence(currentIndex, text.length))
  }
  return annotatedString
}

@PreviewLightDark
@Composable
private fun MarkdownViewPreview() = RouteSearchTheme {
  Surface {
    MarkdownView(
      text =
      """
        # Headline Large
        ## Headline Medium
        ### Headline Small
        
        Here is an example of some body text
        
        - List Item 1
        + List Item 2
        * List Item 3
        
        __Here__ is some **bold** text
        
        _Here_ is some *italics* text
        
        ___Here___ is some ***bold italic*** text
        
        
        [Learn more about Compose](https://developer.android.com/jetpack/compose)
      """.trimIndent(),
    )
  }
}
