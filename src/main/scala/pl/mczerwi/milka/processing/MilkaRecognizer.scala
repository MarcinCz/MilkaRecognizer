package pl.mczerwi.milka.processing

import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.processing.MatImplicits._
import org.opencv.highgui.Highgui
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger


/**
 * @author marcin
 */
object MilkaRecognizer {
  
  val logger = Logger(LoggerFactory.getLogger(getClass.getName))
  
  def apply(fileName: String): Seq[ImageProcessingStage] = {
    val img = Highgui.imread(fileName)
    
    logger.info(s"Starting image processing for file $fileName")
    Seq[ImageProcessingStage](
      ImageProcessingStage("original", img),
      ImageProcessingStage("median", MedianFilter(2)(img))
    )
  
  }
}