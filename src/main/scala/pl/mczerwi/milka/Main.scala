package pl.mczerwi.milka

import org.opencv.highgui.Highgui
import pl.mczerwi.milka.MatImplicits._
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import java.awt.image.ConvolveOp

/**
 * @author marcin
 */
object Main extends App with ImageDisplayer {

  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  val img = Highgui.imread("images/milka1.jpg")
  println(img.get(0, 0))
  
  var medianFilter = MedianFilter(10)
  
  displayImage(img)
//  displayImage(Erosion(2)(Dilatation(2)(img)))
  var lapl = new ConvolveFilter(Seq[Float](
    -1, -1, -1, 
    -1, 8, -1, 
    -1, -1, -1)) 
  
  var filer = new ConvolveFilter(Seq[Float](
     -1, -1, -1,
    -1, 8, -1,
    -1, -1, -1))
    
  displayImage(filer(img))

}