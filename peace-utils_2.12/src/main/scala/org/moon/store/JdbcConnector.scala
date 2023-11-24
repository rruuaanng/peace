package org.moon.store

import java.sql.{Connection, ResultSet}

class JdbcConnector(conn: Connection, tableName: String) extends Connector[Connection, String] {

  override val client: Connection = conn

  private val DQL = client.createStatement()

  override val table: String = tableName

  /**
   * 插入数据
   *
   * @param fields 字段列表
   * @param values 值列表
   */
  def put(fields: List[String], values: List[String]): Unit = {
    // 字段数量和值数量必须一致
    if (fields.size != values.size)
      return
    // 生成?替换符
    val placeholders = fields.map(_ => "?").mkString(", ")

    // 定义插入语句
    val insert = client.prepareStatement(
      s"""INSERT INTO $table
         |(${fields.mkString(", ")})
         |VALUES ($placeholders)
         |""".stripMargin)
    println(values.zipWithIndex)

    // 将插入字段替换到?
    for ((value, index) <- values.zipWithIndex) {
      insert.setString(index + 1, value)
    }
    // 执行插入
    insert.executeUpdate()
  }

  /**
   * 删除数据
   *
   * @param key   行选取字段
   * @param value 行选取值
   */
  def delete(key: String, value: String): Unit = {
    val delete = client.prepareStatement(
      s"""
         |DELETE FROM $table
         |WHERE $key = '$value'
         |""".stripMargin)
    delete.executeUpdate()
  }

  /**
   * 更新数据
   *
   * @param key    行选取字段
   * @param value  行选取值
   * @param fields 修改的字段列表
   * @param values 修改的值列表
   */
  def update(key: String, value: String,
             fields: List[String], values: List[String]): Unit = {
    // 字段数量和值数量必须一致
    if (fields.size != values.size)
      return

    // 生成修改SQL
    val updateList = fields
      .zip(values)
      .map(x => s"""${x._1}='${x._2}'""")
      .mkString(",")

    val update = client.prepareStatement(
      s"""
         |UPDATE $table
         |SET $updateList
         |WHERE $key = '$value'
         |""".stripMargin)
    update.executeUpdate()
  }

  /**
   * 获取单条数据
   *
   * @param field 行选取字段
   * @param value 行选取值
   * @return
   */
  def get(field: String, value: String): ResultSet = {
    DQL.executeQuery(
      s"""
         |SELECT *
         |FROM $table
         |WHERE $field = '$value'
         |""".stripMargin)
  }


  /**
   * 获取所有数据
   *
   * @param fields 字段列表
   * @return
   */
  def gets(fields: List[String]): ResultSet = {
    DQL.executeQuery(
      s"""
         |SELECT ${fields.mkString(",")}
         |from $table
         |""".stripMargin)
  }
}
