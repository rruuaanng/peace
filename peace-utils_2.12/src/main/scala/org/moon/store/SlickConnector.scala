package org.moon.store
// 若需要其他数据库支持更换Profile包即可

import org.moon.common.config.JdbcConfig
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.MySQLProfile.backend.DatabaseDef
import slick.lifted.AbstractTable

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class SlickConnector[T <: AbstractTable[_]](_table: TableQuery[T], _config: JdbcConfig) extends Connector[DatabaseDef, TableQuery[T]] {

  override val table = _table

  override val client = Database.forURL(
    url = _config.getUrl,
    user = _config.getUsername,
    password = _config.getPasswd,
    driver = _config.getDriver)

  /**
   * 插入数据
   *
   * @param value Case类
   */
  def insert(value: T#TableElementType): Unit = {
    val insert = table += value
    val future = client.run(insert)
    Await.result(future, Duration.Inf)
  }

  /**
   * 修改数据
   *
   * @param f     选择函数
   * @param value Case类
   * @example val f: UserModel => Rep[Boolean] = user =>
   *          user.username === "3" || user.passwd === "cc"
   */
  def update(f: T => Rep[Boolean], value: T#TableElementType): Unit = {
    val update = table.filter(f).update(value)
    val future = client.run(update)
    Await.result(future, Duration.Inf)
  }

  /**
   * 获取数据
   *
   * @param f 选择函数
   * @return
   * @example val f: UserModel => Rep[Boolean] = user =>
   *          user.username === "3" || user.passwd === "cc"
   */
  def select(f: T => Rep[Boolean]): Seq[T#TableElementType] = {
    val select = table.filter(f).result
    val future = client.run(select)
    Await.result(future, Duration.Inf)
  }
}
