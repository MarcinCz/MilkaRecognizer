package pl.mczerwi.milka

import org.opencv.core.Core
import org.opencv.highgui.Highgui
import pl.mczerwi.milka.processing.MatImplicits._
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.processing.MedianFilter

/**
 * @author marcin
 */
object Main extends App {

//  var medianFilter = MedianFilter(10)
//  
//  displayImage(img)
////  displayImage(Erosion(2)(Dilatation(2)(img)))
//  var lapl = new ConvolveFilter(Seq[Float](
//    -1, -1, -1, 
//    -1, 8, -1, 
//    -1, -1, -1)) 
//  
//  var filer = new ConvolveFilter(Seq[Float](
//     -1, -1, -1,
//    -1, 8, -1,
//    -1, -1, -1))
//    
////  displayImage(filer(img))
//  var segementator = new PurpleSegmentator
//  displayImage(segementator(img))
  
  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//  val img = Highgui.imread("images/milka1.jpg")
//  
//  val stages = Seq[ImageProcessingStage](
//      ImageProcessingStage("original", img),
//      ImageProcessingStage("median", MedianFilter(2)(img)))
  
  val gui: AppGUI = new AppGUI
  gui.startup(args)

}