package org.moon.http

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.{Await, Future}


class ForwardRequest {

  private var serverResponse: ForwardResponse = _

  /**
   * 转发请求
   *
   * @param host    服务地址
   * @param port    服务端口
   * @param request 请求
   * @return
   */
  private def send(host: String, port: String, request: Request): ForwardRequest = {
    val client = Http.newService(s"$host:$port")
    val response = client(request)
    // 等待异步
    Await.result(response)
    this.serverResponse = new ForwardResponse(response, client)
    this
  }

  def +>(host: String, port: String, request: Request): ForwardRequest =
    this.send(host, port, request)

  /**
   * 处理请求
   *
   * @param f 处理函数
   */
  def process(f: Response => Unit): ForwardRequest = {
    this.serverResponse.getResponse.foreach(f)
    this
  }

  /**
   * 关闭请求
   *
   * @return
   */
  def close: Future[Unit] = this.serverResponse.close
}