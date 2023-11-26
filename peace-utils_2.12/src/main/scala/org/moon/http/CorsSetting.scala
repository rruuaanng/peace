package org.moon.http

import com.twitter.finagle.Http
import com.twitter.finagle.http.filter.Cors
import com.twitter.util.Await

import scala.collection.mutable.ListBuffer

// RESTapi跨域设置

class CorsSetting {
  // 服务列表
  private val corsRests = ListBuffer[(RestApi, String)]()

  private def setting: Cors.Policy = Cors.Policy(
    allowsOrigin = _ => Some("*"),
    allowsMethods = _ => Some(Seq("GET", "POST", "PUT", "DELETE")),
    allowsHeaders = _ => Some(Seq("Content-Type")))

  /**
   * 将服务添加到跨域列表中
   *
   * @param service Rest服务
   * @param port    端口
   * @return
   */
  def add(service: RestApi, port: String): CorsSetting = {
    corsRests.append((service, s":$port"))
    this
  }

  /**
   * 启动服务
   */
  def start(): Unit = {
    // 应用跨域设置到指定RESTapi服务
    val servers = corsRests.map(x => {
      val service = new Cors.HttpFilter(setting)
        .andThen(x._1)
      Http.serve(x._2, service)
    })
    servers.foreach(x => Await.result(x))
  }
}
