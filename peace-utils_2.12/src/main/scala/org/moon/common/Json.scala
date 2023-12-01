package org.moon.common

import io.circe.parser.parse

object Json {

  /**
   * 将Map转换为json字符串
   *
   * @param kvPair 键值对(Map)
   * @return JSON字符串
   * @example Json(Map("hello"->"world"))
   */
  def of(kvPair: Map[String, String]): String = {
    // 将Map中的kv对封装为单个json
    // {
    //   xxx: xxx
    //   xxx: xxx
    //   ...
    // }
    s"{${
      kvPair.map(x =>
          s"""|"${x._1}":"${x._2}"
              |""".stripMargin.stripLineEnd)
        .mkString(",")
    }}"
  }

  /**
   * 将JSON字符串解析为Map
   *
   * @param jsonStr JSON字符串
   * @return
   */
  def chain(jsonStr: String): Map[String, String] = {
    val json = parse(jsonStr)
    json match {
      case Right(x) =>
        // 将键和值合并为字符串
        val keysList = x.hcursor.keys.get.mkString(",")
        // 获取键列表和值列表
        val keys = keysList.split(",")
        // 返回一个Map，其中包含了Json中所有字段和值
        keys.map(k => {
          k -> x.hcursor.get[String](k).getOrElse("")
        }).toMap
      case Left(error) =>
        Map.empty[String, String]
    }
  }
}

