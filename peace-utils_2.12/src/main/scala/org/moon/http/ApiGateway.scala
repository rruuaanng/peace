package org.moon.http

import com.twitter.finagle.{Service, http}


/**
 * API网关接口
 */
trait ApiGateway extends Service[http.Request, http.Response]
