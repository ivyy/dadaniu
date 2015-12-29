package controllers.security

import play.api.http.LazyHttpErrorHandler

class Assets extends controllers.AssetsBuilder(LazyHttpErrorHandler) {}