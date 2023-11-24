package org.moon.http

import com.twitter.finagle.{Service, http}


/**
 * RESTapi接口
 */
trait RestApi extends Service[http.Request, http.Response]
