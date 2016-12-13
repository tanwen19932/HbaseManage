package learning._21Continuations

/**
	* @author TW
	* @date TW on 2016/12/10.
	*/
class _20Continuations {

}

object _20Continuations {
	def main(args: Array[String]): Unit = {
		var cont: (Unit => Unit) = null
		reset {
			println("reset begin")
			shift {
						println("shift begin")
				k: (Int => Int) => k(8)
			} + 1
			println("after shift")
		}
	}

}