package pl.mczerwi.milka

import org.opencv.core.Mat
import scala.collection.mutable.ListBuffer
import java.awt.image.RescaleOp

/**
 * @author marcin
 */
class ConvolveFilter(mask: Seq[Float]) extends ImageProcessor {
  
  private[milka] val maskDim: Int = {
    Math.sqrt(mask.size) match  {
      case m: Double if m % 1 == 0 => Math.round(m.toFloat)
      case _ => throw new IllegalArgumentException("Wrong mask size: " + mask.size)
    }
  }
    
  def apply(image: Mat): Mat = {
    val imageCopy = copyImage(image)
    var range: Int = maskDim / 2
    for (x <- range until imageCopy.rows() - range; y <- range until imageCopy.cols() - range) {
      var redSum = 0f
      var greenSum= 0f
      var blueSum = 0f
      var maskPosition = 0
      for(i <- x - range to x + range; j <- y - range to y + range) {
        blueSum += image.get(i, j)(0).toInt * mask(maskPosition)
        greenSum += image.get(i, j)(1).toInt * mask(maskPosition)
        redSum += image.get(i, j)(2).toInt * mask(maskPosition)
        maskPosition += 1
      }
      putPixel(imageCopy, x, y, new Pixel(calcColorVal(redSum), calcColorVal(greenSum), calcColorVal(blueSum)))
    }
    imageCopy
  }
  
  private[milka] def calcColorVal(colorSum: Float): Int = {
    var color: Int = Math.round(colorSum)
      color match {
        case c if c > 255 => 255
        case c if c < 0 => 0
        case c => c
      }
  }

}