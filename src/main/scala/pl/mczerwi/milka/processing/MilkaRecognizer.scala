package pl.mczerwi.milka.processing

import scala.collection.mutable.ListBuffer

import org.opencv.core.Mat
import org.opencv.highgui.Highgui
import org.slf4j.LoggerFactory

import com.typesafe.scalalogging.Logger

import pl.mczerwi.milka.processing.MatImplicits.mat2BufferedImage


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
    
    def timeAndAddStageForSplitter(title: String, f: => ImageObjectSplitterResult) = {
      val result = time(title, f)
      processingStages += ImageProcessingStage(title, result.image)
      result
    }
    
    def timeAndAddStageForIndentificator(title: String, f: => IdentificatorResult) = {
      val result = time(title, f)
      processingStages += ImageProcessingStage(title, result.image)
      result
    }
    
    val original = timeAndAddStage("Original image read", Highgui.imread(fileName))
//    val histEqualized = timeAndAddStage("Histogram equalized", HistogramEqualizer()(original))
    val medianImg = timeAndAddStage("Gauss filter", GaussFilter(original))
//    val sharpnedImg = timeAndAddStage("Sharpen filter", SharpenFilter(medianImg))
    val purpleImg = timeAndAddStage("Purple segments", PurpleSegmentator()(medianImg))
    val purpleMedian = timeAndAddStage("Median filter", MedianFilter(2)(purpleImg))
    val purpleDilation = timeAndAddStage("Purple dilation", Dilation(1)(purpleMedian))
    val purpleErosion = timeAndAddStage("Purple erosion", Erosion(3)(purpleMedian))
    val purpleObjectSpliting = timeAndAddStageForSplitter("Purple object splitting", ImageObjectSplitter()(purpleErosion, 250))
    val whiteImg = timeAndAddStage("White segments", WhiteSegmentator()(medianImg))
    val whiteDilation = timeAndAddStage("White median", MedianFilter(1)(whiteImg))
    val whiteErosion = timeAndAddStage("White erosion", Erosion(1)(whiteDilation))
    val whiteObjectSpliting = timeAndAddStageForSplitter("White object splitting", ImageObjectSplitter()(whiteErosion, 50))
    val leftPartIdentified = timeAndAddStageForIndentificator("Left part identified", LeftPartIdentificator()(whiteObjectSpliting.image, whiteObjectSpliting.objects))
    val rightPartIdentified = timeAndAddStageForIndentificator("Right part identified", RightPartIdentificator()(whiteObjectSpliting.image, whiteObjectSpliting.objects))
    val logoMarked = timeAndAddStage("Recognition result", FullLogoFinder()(original, purpleObjectSpliting.objects, leftPartIdentified.identifiedObjects, rightPartIdentified.identifiedObjects))

    processingStages
  }
}