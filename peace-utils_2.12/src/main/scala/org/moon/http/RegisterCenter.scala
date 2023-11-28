package org.moon.http

/**
 * 注册中心
 */
trait RegisterCenter {
  def register(name: String, host: String, port: String): Unit

  def discover(name: String): (String, String)
}