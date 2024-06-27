package com.routesearch.data.image

import android.content.Context
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.routesearch.util.common.exception.ImageLoadingException
import logcat.LogPriority.DEBUG
import logcat.LogPriority.WARN
import logcat.asLog
import logcat.logcat

internal class CoilImageLoader(
  private val context: Context,
  private val coilImageLoader: coil.ImageLoader,
) : ImageLoader {

  override suspend fun load(url: String) {
    val request = buildImageRequest(url)
    coilImageLoader.execute(request)
  }

  private fun buildImageRequest(url: String) = ImageRequest.Builder(context)
    .listener(
      onStart = ::onImageLoading,
      onSuccess = { request, _ -> onImageLoadingSuccess(request = request) },
      onError = ::onImageLoadingError,
    )
    .data(url)
    .build()

  private fun onImageLoading(request: ImageRequest) = logcat(DEBUG) { "Loading image ${request.data}" }

  private fun onImageLoadingSuccess(
    request: ImageRequest,
  ) = logcat(DEBUG) { "Done loading image ${request.data}" }

  private fun onImageLoadingError(
    request: ImageRequest,
    result: ErrorResult,
  ) = logcat(WARN) {
    ImageLoadingException(
      message = "Error loading image ${request.data}",
      cause = result.throwable,
    ).asLog()
  }
}
