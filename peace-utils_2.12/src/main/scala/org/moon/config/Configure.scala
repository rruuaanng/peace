package org.moon.config

import com.typesafe.config.{Config, ConfigFactory, ConfigList}


/**
 * 配置文件解析
 *
 * @param name 配置文件
 */
class Configure(name: String) {

  private val configFile: Config = ConfigFactory.load(name)

  /**
   * 获取Config对象
   *
   * @return Config
   */
  def getConfig: Config = configFile

  /**
   * 获取配置文件中的字段信息
   *
   * @param group 字段组
   * @param key   键值
   * @return
   */
  def getSection(group: String, key: String): String =
    configFile.getString(s"$group.$key")

  /**
   * 获取配置文件中指定的字段值
   *
   * @param key 字段名
   * @return
   */
  def getKey(key: String): String = configFile.getString(key)

  /**
   * 获取配置文件中的字段列表
   *
   * @param key 字段名
   * @return
   */
  def getList(key: String): ConfigList = configFile.getList(key)
}


object Configure {

  final private val DEFAULT_FILE = "local.conf"

  /**
   * 解析配置文件
   *
   * @param name 文件名称
   * @return
   * @note 默认读取local.conf
   */
  def apply(name: String = DEFAULT_FILE): Configure = new Configure(name)
}