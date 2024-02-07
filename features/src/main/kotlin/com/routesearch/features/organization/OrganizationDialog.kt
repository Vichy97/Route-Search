package com.routesearch.features.organization

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility.Companion.Gone
import androidx.constraintlayout.compose.Visibility.Companion.Visible
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.routesearch.features.R
import com.routesearch.ui.common.compose.annotation
import com.routesearch.ui.common.compose.isAnnotatedAtIndex
import com.routesearch.ui.common.compose.underline
import com.routesearch.ui.common.theme.RouteSearchTheme
import org.koin.androidx.compose.koinViewModel

@Destination(
  navArgsDelegate = OrganizationScreenArgs::class,
  style = DestinationStyle.Dialog.Default::class,
)
@Composable
internal fun OrganizationDialog() {
  val viewModel = koinViewModel<OrganizationViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  OrganizationDialogContent(
    viewState = viewState,
    onWebsiteClick = viewModel::onWebsiteClick,
    onFacebookClick = viewModel::onFacebookClick,
    onInstagramClick = viewModel::onInstagramClick,
  )
}

@Composable
private fun OrganizationDialogContent(
  modifier: Modifier = Modifier,
  viewState: OrganizationViewState,
  onWebsiteClick: () -> Unit,
  onFacebookClick: () -> Unit,
  onInstagramClick: () -> Unit,
) = ElevatedCard(
  modifier = modifier
    .wrapContentHeight()
    .fillMaxWidth(),
) {
  ConstraintLayout(
    modifier = Modifier.padding(top = 16.dp),
  ) {
    val (title, websiteUrl, description, divider, facebookButton, instagramButton) = createRefs()

    Text(
      modifier = Modifier
        .constrainAs(title) {
          top.linkTo(parent.top)
          start.linkTo(parent.start)
        }
        .padding(horizontal = 16.dp),
      text = viewState.name,
      style = MaterialTheme.typography.titleMedium,
    )

    WebsiteText(
      modifier = Modifier
        .constrainAs(websiteUrl) {
          top.linkTo(title.bottom)
          start.linkTo(parent.start)

          visibility = if (viewState.websiteUrl.isNullOrBlank()) Gone else Visible
        }
        .padding(horizontal = 16.dp),
      text = viewState.websiteUrl ?: "",
      onClick = onWebsiteClick,
    )

    Text(
      modifier = Modifier
        .constrainAs(description) {
          top.linkTo(
            anchor = websiteUrl.bottom,
            margin = 8.dp,
          )
          start.linkTo(parent.start)

          visibility = if (viewState.description.isNullOrBlank()) Gone else Visible
        }
        .padding(horizontal = 16.dp)
        .padding(bottom = 16.dp)
        .heightIn(max = 200.dp)
        .verticalScroll(rememberScrollState()),
      text = viewState.description ?: "",
      style = MaterialTheme.typography.bodyMedium,
    )

    Divider(
      modifier = Modifier.constrainAs(divider) {
        top.linkTo(description.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)

        width = Dimension.fillToConstraints
        visibility = if (
          viewState.facebookUrl.isNullOrBlank() &&
          viewState.instagramUrl.isNullOrBlank()
        ) {
          Gone
        } else {
          Visible
        }
      },
    )

    TextButton(
      modifier = Modifier.constrainAs(facebookButton) {
        top.linkTo(divider.bottom)
        start.linkTo(parent.start)

        visibility = if (viewState.facebookUrl.isNullOrBlank()) Gone else Visible
      },
      content = { Text(stringResource(R.string.organization_dialog_facebook_button_title)) },
      onClick = onFacebookClick,
    )

    TextButton(
      modifier = Modifier.constrainAs(instagramButton) {
        top.linkTo(divider.bottom)
        start.linkTo(facebookButton.end)

        visibility = if (viewState.instagramUrl.isNullOrBlank()) Gone else Visible
      },
      content = { Text(stringResource(R.string.organization_dialog_instagram_button_title)) },
      onClick = onInstagramClick,
    )
  }
}

@Composable
private fun WebsiteText(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit,
) {
  val websiteAnnotation = "website"
  val locationString = buildAnnotatedString {
    underline {
      annotation(websiteAnnotation) {
        append(text)
      }
    }
  }
  ClickableText(
    modifier = modifier,
    text = locationString,
    style = MaterialTheme.typography.bodyMedium.copy(
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    ),
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
  ) {
    val annotationClicked = locationString.isAnnotatedAtIndex(
      index = it,
      annotation = websiteAnnotation,
    )
    if (annotationClicked) onClick()
  }
}

@PreviewLightDark
@Composable
private fun OrganizationDialogPreview() = RouteSearchTheme {
  Surface(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Dialog(
      onDismissRequest = { },
    ) {
      OrganizationDialogContent(
        modifier = Modifier.padding(16.dp),
        viewState = OrganizationViewState(
          name = "Central Arizona Bolt Replacement Program",
          websiteUrl = "https://cabrp.org",
          description = """
            The Central Arizona Bolt Replacement Program (CABRP) is an accredited 501(c)(3) organization. It was created
            to preserve the safety and integrity of fixed protection throughout central Arizona. Since its creation, 
            the CABRP has replaced hundreds of worn and aged lead bolts and anchors. The fixed anchors used to create 
            most of the sport climbs in the US are made of non-stainless steel. As such, they are subject to corrosion 
            that weakens the anchors holding power. The non-stainless steel bolts throughout central Arizona have been 
            exposed to environmental factors since the first day they were placed. All told, there are approximately 
            2,755 total routes throughout central Arizona. Working with the American Safe Climbing Association (ASCA), 
            CABRP is placing stainless steel glue-in bolts and stainless steel expansion anchors specifically designed 
            for the rigors of our sport. CABRP requests that all climbers establishing new routes use modern, stainless 
            steel equipment. CABRP recognizes that this makes establishing a new route more expensive, but it is also 
            the responsible way to protect future climbers and the precious resource that is our rock walls. 
          """.trimIndent(),
          facebookUrl = "https://facebook.com/cabrp",
          instagramUrl = "https://instagram.com/cabrp",
        ),
        onWebsiteClick = { },
        onFacebookClick = { },
        onInstagramClick = { },
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun OrganizationDialogMinimalPreview() = RouteSearchTheme {
  Surface(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Dialog(
      onDismissRequest = { },
    ) {
      OrganizationDialogContent(
        modifier = Modifier.padding(16.dp),
        viewState = OrganizationViewState(
          name = "Central Arizona Bolt Replacement Program",
          websiteUrl = "https://cabrp.org",
          description = null,
          facebookUrl = null,
          instagramUrl = null,
        ),
        onWebsiteClick = { },
        onFacebookClick = { },
        onInstagramClick = { },
      )
    }
  }
}
