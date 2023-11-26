package org.moon.store

import org.moon.common.config.JdbcConfig
import slick.lifted.{AbstractTable, TableQuery}

import java.sql.DriverManager

object ConnectorFactory {
  /**
   * 创建JDBC连接器
   *
   * @param tableName 表名
   * @param config    JDBC配置
   * @return
   */
  def createJdbc(tableName: String, config: JdbcConfig): JdbcConnector = {
    // 注册驱动类
    Class.forName(config.getDriver)

    new JdbcConnector(DriverManager.getConnection(
      config.getUrl,
      config.getUsername,
      config.getPasswd), tableName)
  }

  /**
   * 创建Slick连接器
   *
   * @param table  表对象
   * @param config JDBC配置
   * @tparam T 表模型
   * @return
   */
  def createSlick[T <: AbstractTable[_]](table: TableQuery[T], config: JdbcConfig): SlickConnector[T] =
    new SlickConnector[T](table, config)
}
