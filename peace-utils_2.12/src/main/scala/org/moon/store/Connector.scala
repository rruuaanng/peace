package org.moon.store

/**
 * 连接器
 *
 * @tparam T1 客户端类型
 * @tparam T2 表类型
 */
trait Connector[T1, T2] {
  val client: T1
  val table: T2
}
