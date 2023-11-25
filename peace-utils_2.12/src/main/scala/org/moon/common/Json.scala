package org.moon.common

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
}

