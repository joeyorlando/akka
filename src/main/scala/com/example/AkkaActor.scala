package com.example

import akka.actor.{Actor, ActorSystem, Props, Status}
import akka.event.{Logging, LoggingAdapter}
import com.example.messages.{GetRequest, KeyNotFoundException, SetRequest}

import scala.collection.mutable

class AkkaActor extends Actor {
  val map = new mutable.HashMap[String, Object]()
  val log: LoggingAdapter = Logging(context.system, this)

  override def receive: Receive = {
    case SetRequest(key, value) =>
      log.info("received SetRequest - key: {} value: {}", key, value)
      map.put(key, value)
      sender() ! Status.Success
    case GetRequest(key) =>
      log.info("received GetRequest - key: {}", key)
      val response: Option[Object] = map.get(key)
      response match{
        case Some(x) => sender() ! x
        case None => sender() ! Status.Failure(KeyNotFoundException(key))
      }
    case o => log.info("received unknown message: {}", o)
  }
}

object Main extends App {
  val system = ActorSystem("akkademy")
  system.actorOf(Props[AkkaActor], name = "akkademy-db")
}