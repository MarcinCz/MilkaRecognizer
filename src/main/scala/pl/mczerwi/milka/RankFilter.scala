package pl.mczerwi.milka

import org.opencv.core.Mat
import pl.mczerwi.milka.Pixel._
import org.opencv.core.Core
import org.opencv.core.CvType
import scala.collection.mutable.ListBuffer

/**
 * @author marcin
 */
class RankFilter(range: Int, selectF: Seq[Pixel] => Pixel) extends ImageProcessor {
  
  def apply(image: Mat): Mat = {
    val imageCopy = copyImage(image)
    for (x <- range until imageCopy.rows() - range; y <- range until imageCopy.cols() - range) {
      var pixelsToSelectFrom: ListBuffer[Pixel] = new ListBuffer[Pixel]
      for(i <- x - range to x + range; j <- y - range to y + range) {
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

object Dilatation {
    def apply(range: Int) = new RankFilter(range, (pixels: Seq[Pixel]) => pixels.last)
}

object Erosion {
    def apply(range: Int) = new RankFilter(range, (pixels: Seq[Pixel]) => pixels.head)
}