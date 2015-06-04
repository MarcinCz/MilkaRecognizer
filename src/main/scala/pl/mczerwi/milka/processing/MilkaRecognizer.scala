package pl.mczerwi.milka.processing

import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.processing.MatImplicits._
import org.opencv.highgui.Highgui
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger
import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.gui.ImageProcessingStage
import scala.collection.mutable.ListBuffer
import org.opencv.core.Mat
import pl.mczerwi.milka.gui.ImageProcessingStage
import java.awt.image.BufferedImage


/**
 * @author marcin
 */
object MilkaRecognizer extends TimePrinter {
  
  private val logger = Logger(LoggerFactory.getLogger(getClass.getName))
  
  def apply(fileName: String): Seq[ImageProcessingStage] = {
	  logger.info(s"Starting image processing for file $fileName")
    
    var processingStages: ListBuffer[ImageProcessingStage] = new ListBuffer[ImageProcessingStage]
    
    def timeAndAddStage(title: String, f: => Mat) = {
      val image = time(title, f)
      processingStages += ImageProcessingStage(title, image)
      image
    }
    
    def timeAndAddStageForSplitter(title: String, f: => SegmentSplitterResult) = {
      val result = time(title, f)
      processingStages += ImageProcessingStage(title, result.image)
      result
    }
    
    val original = timeAndAddStage("Original image read", Highgui.imread("images/milka2.png"))
//    val histEqualized = timeAndAddStage("Histogram equalized", HistogramEqualizer()(original))
    val medianImg = timeAndAddStage("Gauss filter", GaussFilter(original))
//    val sharpnedImg = timeAndAddStage("Sharpen filter", SharpenFilter(medianImg))
    val purpleImg = timeAndAddStage("Purple segments", PurpleSegmentator()(medianImg))
    val purpleMedian = timeAndAddStage("Median filter", MedianFilter(2)(purpleImg))
    val purpleDilation = timeAndAddStage("Purple dilation", Dilation(1)(purpleMedian))
    val purpleErosion = timeAndAddStage("Purple erosion", Erosion(3)(purpleMedian))
    val purpleSegmentSpliting = timeAndAddStageForSplitter("Purple segment splitting", SegmentSplitter()(purpleErosion, 250))
    processingStages
  
  }
}