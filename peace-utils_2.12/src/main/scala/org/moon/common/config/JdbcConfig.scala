package org.moon.common.config

class JdbcConfig(url: String, username: String, passwd: String, driver: String) {

  def getUrl: String = url

  def getUsername: String = username

  def getPasswd: String = passwd

  def getDriver: String = driver

  override def toString: String =
    s"url: $url\n" +
      s"username: $username\n" +
      s"passwd: $passwd\n" +
      s"driver: $driver\n"
}

object JdbcConfig {
  /**
   * 创建JDBC配置项
   *
   * @note 构建者接口
   * @return 构建器
   */
  def apply(): JdbcBuilder = new JdbcBuilder()

  class JdbcBuilder {
    private var config: JdbcConfigCase = JdbcConfigCase("", "", "", "", "", "", "")

    def withHost(host: String): JdbcBuilder = {
      config = config.copy(host = host)
      this
    }

    def withPort(port: String): JdbcBuilder = {
      config = config.copy(port = port)
      this
    }

    def withUser(username: String): JdbcBuilder = {
      config = config.copy(username = username)
      this
    }

    def withPasswd(passwd: String): JdbcBuilder = {
      config = config.copy(passwd = passwd)
      this
    }

    def withDbName(dbName: String): JdbcBuilder = {
      config = config.copy(dbName = dbName)
      this
    }

    def withDbType(dbType: String): JdbcBuilder = {
      config = config.copy(dbType = dbType)
      this
    }

    def withDriver(driver: String): JdbcBuilder = {
      config = config.copy(driver = driver)
      this
    }

    def build(): JdbcConfig = {
      new JdbcConfig(s"jdbc:${config.dbType}://" +
        s"${config.host}:" +
        s"${config.port}/" +
        s"${config.dbName}",
        config.username, config.passwd, config.driver)
    }
  }
}

case class JdbcConfigCase(host: String, port: String,
                          username: String, passwd: String,
                          dbName: String, dbType: String,
                          driver: String)