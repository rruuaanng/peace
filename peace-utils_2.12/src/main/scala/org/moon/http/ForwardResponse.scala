package org.moon.http

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Future

class ForwardResponse(_response: Future[Response], _client: Service[Request, Response]) {

  private val response = _response

  private val client = _client

  def getResponse: Future[Response] = response

  def close: Future[Unit] = client.close()

}