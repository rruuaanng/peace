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
  private def Req(adder: String, path: String,
                  method: String, body: String): String = {
    val client = Http.newService(adder)
    var request: Option[Request] = None
    // 选择请求方法
    method match {
      case "POST" => request = Option(Request(Method.Post, path))
      case "PUT" => request = Option(Request(Method.Put, path))
      case "DELETE" => request = Option(Request(Method.Put, path))
    }

    request.get.setContentString(body)

    val future = client(request.get)
    val response = Await.result(future)
    response.getContentString()
  }

  def +>(adder: String, path: String): String = getReq(adder, path)

  def +>(adder: String, path: String, method: String, body: String): String =
    Req(adder, path, method, body)

}
