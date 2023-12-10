package org.moon.actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.reflect.ClassTag

object AkkaUtils {
  def getActor[T <: Actor : ClassTag](host: String, port: String,
                                      name: String): ActorRef = {
    val conf =
      s"""|akka.actor.provider = remote
          |akka.remote.netty.tcp.hostname = $host
          |akka.remote.netty.tcp.port = $port"""
        .stripMargin
        .stripLineEnd

    val config = ConfigFactory.parseString(conf)

    ActorSystem(name, config).actorOf(Props[T])
  }
}
