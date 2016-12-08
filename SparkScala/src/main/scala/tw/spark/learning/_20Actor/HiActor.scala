package tw.spark.learning._20Actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.event.Logging

class MyActor extends Actor {
	val log = Logging(context.system, this)

	def receive = {
		case "test" => log.info("received test"); sender ! ">>"
		case _      => log.info("received unknown message")
	}


object HiActor {
	def main(args: Array[String]): Unit = {
		val hiActor = new MyActor
		hiActor.preStart()
		hiActor ! "test"
	}
}