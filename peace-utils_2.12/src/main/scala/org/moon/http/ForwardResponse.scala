package org.moon.http

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

class ForwardResponse(response: Future[Response], client: Service[Request, Response]) {

  def getResponse: Future[Response] = response

  def close: Future[Unit] = client.close()

}