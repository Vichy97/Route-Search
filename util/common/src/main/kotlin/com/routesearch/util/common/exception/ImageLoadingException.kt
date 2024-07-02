package com.routesearch.util.common.exception

import java.lang.RuntimeException

class ImageLoadingException(
  message: String,
  cause: Throwable,
) : RuntimeException(message, cause)
