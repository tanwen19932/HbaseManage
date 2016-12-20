package jwtech.spark.streaming.receiver.news

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.json.JSONObject
import org.slf4j.LoggerFactory


/**
	* @author TW
	*/
class HbaseReceiver extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) {
	val LOG = HbaseReceiver.LOG

	override def onStart() {
		println("干活干活！！！！")
		new Thread("Socket Receiver") {
			override def run() {
				receive()
			}
		}.start()
	}

	override def onStop(): Unit = {
		println("把我停止了？？？？")
	}

	private def receive(): Unit = {
		try {
			println("Receive RDD-TW")
			val title = "HolyShit"
			val text = "The mother of a British teenager found dead on a beach in Goa eight years ago has vowed to fight on after the two men charged with murdering her daughter were acquitted by an Indian court today. " + "\n<br>" + "Scarred with dozens of injuries, the body of 15-year-old Scarlett Keeling, from Devon, was found soon after dawn on Anjuna beach in Goa on February 18, 2008." + "\n<br>" + "Two beach-shack workers, Samson D’Souza, 30, and Placido Carvalho, 42, were accused of plying Ms Keeling with a cocktail of drugs and alcohol before raping her and leaving her unconscious in the shallows where she drowned.…" + "Scarlett’s mother, Fiona MacKeown, said she would continue to fight for justice for her daughter INDRANIL MUKHERJEE/AFP/GETTY IMAGES：\n"
			val pubDate = HbaseReceiver.sdf.format(HbaseReceiver.calendar.getTime)
			HbaseReceiver.calendar.add(Calendar.SECOND, 10)
			val json = new JSONObject
			json.put("title", title)
			json.put("text", text)
			json.put("pubDate", pubDate)
			store(json.toString())
		}
		catch {
			case e: java.net.ConnectException =>
			case t: Throwable => ;
		}
	}
}

object HbaseReceiver {
	val LOG = LoggerFactory.getLogger(classOf[HbaseReceiver])
	val calendar = new java.util.GregorianCalendar()
	val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
}
