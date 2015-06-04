package pl.mczerwi.milka.processing

import org.opencv.core.Mat
import org.opencv.core.Core
import org.opencv.core.CvType
import scala.collection.mutable.ListBuffer

/**
 * @author marcin
 */
class RankFilter(range: Int, selectF: Seq[Pixel] => Pixel) extends ImageProcessor {
  
  def apply(image: Mat): Mat = {
    val imageCopy = copyImage(image)
    for (x <- 0 until imageCopy.rows(); y <- 0 until imageCopy.cols()) {
      var pixelsToSelectFrom: ListBuffer[Pixel] = new ListBuffer[Pixel]
      
      val minRow = if(x - range < 0) 0 else x - range;
      val maxRow = if(x + range >= imageCopy.rows()) imageCopy.rows() else x + range;
      val minCol = if(y - range < 0) 0 else y - range;
      val maxCol = if(y + range >= imageCopy.cols()) imageCopy.cols() else y + range;

      for(i <- minRow until maxRow; j <- minCol until maxCol) {
        	pixelsToSelectFrom += Pixel(image.get(i, j))
      }
      pixelsToSelectFrom = pixelsToSelectFrom.sortBy(p => p.blue + p.green + p.red)
      putPixel(imageCopy, x, y, selectF(pixelsToSelectFrom))
    }
    imageCopy
  }
  
}

object MedianFilter {
    def apply(range: Int) = new RankFilter(range, (pixels: Seq[Pixel]) => pixels(pixels.size/2))
}

object Dilation {
    def apply(range: Int) = new RankFilter(range, (pixels: Seq[Pixel]) => pixels.last)
}

object Erosion {
    def apply(range: Int) = new RankFilter(range, (pixels: Seq[Pixel]) => pixels.head)
}