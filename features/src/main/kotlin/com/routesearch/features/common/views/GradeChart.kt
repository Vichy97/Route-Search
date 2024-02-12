@file:Suppress("SpreadOperator", "CyclomaticComplexMethod", "MagicNumber")

package com.routesearch.features.common.views

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.shape.roundedCornerShape
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.routesearch.ui.common.theme.RouteSearchTheme
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun YdsGradeChart(
  modifier: Modifier = Modifier,
  gradeMap: ImmutableMap<String, Int>,
) {
  val entryModel = remember { entryModelOfYds(gradeMap) }

  GradeChart(
    modifier = modifier,
    entryModel = entryModel,
    valueFormatter = ydsValueFormatter,
  )
}

@Composable
internal fun VScaleGradeChart(
  modifier: Modifier = Modifier,
  gradeMap: ImmutableMap<String, Int>,
) {
  val entryModel = remember { entryModelOfVScale(gradeMap) }

  GradeChart(
    modifier = modifier,
    entryModel = entryModel,
    valueFormatter = vScaleValueFormatter,
  )
}

@Composable
private fun GradeChart(
  modifier: Modifier = Modifier,
  entryModel: ChartEntryModel,
  valueFormatter: AxisValueFormatter<AxisPosition.Horizontal.Bottom>,
) = Chart(
  modifier = modifier,
  chart = columnChart(
    columns = listOf(
      LineComponent(
        color = MaterialTheme.colorScheme.secondary.toArgb(),
        thicknessDp = 5f,
        shape = Shapes.roundedCornerShape(
          topLeft = 4.dp,
          topRight = 4.dp,
        ),
      ),
    ),
    spacing = 1.dp,
  ),
  model = entryModel,
  isZoomEnabled = false,
  startAxis = null,
  bottomAxis = rememberBottomAxis(
    label = TextComponent.Builder().apply {
      color = MaterialTheme.colorScheme.onSurface.toArgb()
      textSizeSp = 10f
    }.build(),
    guideline = null,
    tick = null,
    valueFormatter = valueFormatter,
  ),
)

@PreviewLightDark
@Composable
private fun YdsChartPreview() = RouteSearchTheme {
  Surface {
    YdsGradeChart(
      modifier = Modifier
        .heightIn(
          max = 150.dp,
        )
        .padding(16.dp),
      gradeMap = persistentMapOf(
        "5.4" to 1,
        "5.5" to 3,
        "5.6" to 8,
        "5.7" to 21,
        "5.8" to 35,
        "5.9" to 7,
        "5.10" to 92,
        "5.11" to 23,
        "5.12" to 6,
        "5.13" to 1,
      ),
    )
  }
}

@PreviewLightDark
@Composable
private fun VScaleChartPreview() = RouteSearchTheme {
  Surface {
    VScaleGradeChart(
      modifier = Modifier
        .heightIn(
          max = 150.dp,
        )
        .padding(16.dp),
      gradeMap = persistentMapOf(
        "V1" to 1,
        "V2" to 3,
        "V3" to 8,
        "V4" to 21,
        "V5" to 35,
        "V6" to 7,
        "V7" to 92,
        "V8" to 23,
        "V9" to 6,
        "V10" to 1,
        "V12" to 3,
        "V13" to 1,
      ),
    )
  }
}

private val ydsLabels = arrayOf("<5.6", "5.7", "5.8", "5.9", "5.10", "5.11", "5.12", "5.13", ">5.14")
private val ydsValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
  val index = value.toInt()

  // Only display even labels for less clutter
  ydsLabels[index].takeIf { index % 2 == 0 } ?: ""
}

private fun entryModelOfYds(gradeMap: Map<String, Int>): ChartEntryModel {
  val counts = IntArray(9) { 0 }

  gradeMap.forEach { (grade, count) ->
    val index = indexOfYds(grade)

    if (index >= 0) {
      counts[index] += count
    }
  }

  return entryModelOf(*counts.toTypedArray())
}

private fun indexOfYds(grade: String) = when {
  arrayOf("5.14", "5.15").any(grade::contains) -> 8
  grade.contains("5.13") -> 7
  grade.contains("5.12") -> 6
  grade.contains("5.11") -> 5
  grade.contains("5.10") -> 4
  grade.contains("5.9") -> 3
  grade.contains("5.8") -> 2
  grade.contains("5.7") -> 1
  arrayOf("5.0", "5.1", "5.2", "5.3", "5.4", "5.5", "5.6").any(grade::contains) -> 0
  else -> -1
}

private val vScaleLabels = arrayOf("<V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12", ">V13")
private val vScaleValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
  val index = value.toInt()

  // Only display even labels for less clutter
  vScaleLabels[index].takeIf { index % 2 == 0 } ?: ""
}

private fun entryModelOfVScale(gradeMap: Map<String, Int>): ChartEntryModel {
  val counts = IntArray(13) { 0 }

  gradeMap.forEach { (grade, count) ->
    val index = indexOfVScale(grade)

    if (index >= 0) {
      counts[index] += count
    }
  }

  return entryModelOf(*counts.toTypedArray())
}

private fun indexOfVScale(grade: String) = when {
  arrayOf("V15", "V14", "V13").any(grade::contains) -> 12
  grade.contains("V12") -> 11
  grade.contains("V11") -> 10
  grade.contains("V10") -> 9
  grade.contains("V9") -> 8
  grade.contains("V8") -> 7
  grade.contains("V7") -> 6
  grade.contains("V6") -> 5
  grade.contains("V5") -> 4
  grade.contains("V4") -> 3
  grade.contains("V3") -> 2
  grade.contains("V2") -> 1
  arrayOf("V1", "V0").any(grade::contains) -> 0
  else -> -1
}
