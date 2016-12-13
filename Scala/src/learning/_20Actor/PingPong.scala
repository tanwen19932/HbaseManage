//package tw.spark.learning._20Actor
//
///**
//	* @author TW
//	* @date TW on 2016/12/8.
//	*/
//import javafx.scene.paint.Stop
//
//import org.apache.avro.echo.{Ping, Pong}
//
//import scala.actors._
//
///**
//	* @author TW
//	* @date TW on 2016/12/8.
//	*/
//class Ping(count: Int, pong: Actor) extends Actor {
//	def act() {
//		var pingsLeft = count - 1
//		pong ! Ping
//		while (true) {
//			receive {
//				case Pong =>
//					if (pingsLeft % 1000 == 0)
//						Console.println("Ping: pong")
//					if (pingsLeft > 0) {
//						pong ! Ping
//						pingsLeft -= 1
//					} else {
//						Console.println("Ping: stop")
//						pong ! Stop
//						exit()
//					}
//			}
//		}
//	}
//}
//
//class Pong extends Actor {
//	def act() {
//		var pongCount = 0
//		while (true) {
//			receive {
//				case Ping =>
//					if (pongCount % 1000 == 0)
//						Console.println("Pong: ping " + pongCount)
//					sender ! Pong
//					pongCount = pongCount + 1
//				case Stop =>
//					Console.println("Pong: stop")
//					exit()
//			}
//		}
//	}
//}
//
//object pingpong {
//	def main(args: Array[String]): Unit = {
//		val pong = new Pong
//		val ping = new Ping(100000, pong)
//		ping.start
//		pong.start
//	}
//}
