package org.moon.http

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request}
import com.twitter.util.Await

object NetReq {

  /**
   * 发送GET请求到目标服务
   *
   * @param adder 服务地址
   * @param path  请求路由
   * @return
   */
  private def getReq(adder: String, path: String): String = {
    val client = Http.newService(adder)
    val request = Request(Method.Get, path)
    val future = client(request)

    val response = Await.result(future)
    response.getContentString()
  }

  /**
   * 发送POST到目标服务
   *
   * @param adder 服务地址
   * @param path  请求路由
   * @param body  报文主体
   * @return
   */
  private def postReq(adder: String, path: String, body: String): String = {
    val client = Http.newService(adder)
    val request = Request(Method.Get, path)
    request.setContentString(body)

    val future = client(request)
    val response = Await.result(future)
    response.getContentString()
  }

  def +>(adder: String, path: String): String = getReq(adder, path)

  def +>(adder: String, path: String, body: String): String = postReq(adder, path, body)

}
