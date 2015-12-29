package controllers.stock

import play.api.http.LazyHttpErrorHandler

class Assets extends controllers.AssetsBuilder(LazyHttpErrorHandler) {}