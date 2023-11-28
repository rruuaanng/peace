package org.moon.http

import com.twitter.finagle.{Service, http}

trait Server extends Service[http.Request, http.Response]