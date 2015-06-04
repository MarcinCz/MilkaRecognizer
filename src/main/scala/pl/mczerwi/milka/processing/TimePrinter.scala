package pl.mczerwi.milka.processing

import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

/**
 * @author marcin
 */
trait TimePrinter {
  
    private val logger = Logger(LoggerFactory.getLogger(getClass.getName))

    def printTime(name: String, time: Long) = {
      logger.info(name + " time: "+(time)/1e6+"ms")
    }
    
    def time[A](name: String, f: => A) = {
      val s = System.nanoTime
      val ret = f
      printTime(name, System.nanoTime - s)
      ret
    }
}