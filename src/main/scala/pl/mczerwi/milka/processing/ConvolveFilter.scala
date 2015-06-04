package pl.mczerwi.milka.processing

import org.opencv.core.Mat
import scala.collection.mutable.ListBuffer
import java.awt.image.RescaleOp

/**
 * @author marcin
 */
class ConvolveFilter(mask: Seq[Double]) extends ImageProcessor {
  
  private[processing] val maskDim: Int = {
    Math.sqrt(mask.size) match  {
      case m: Double if m % 1 == 0 => Math.round(m.toFloat)
      case _ => throw new IllegalArgumentException("Wrong mask size: " + mask.size)
    }
  }
    
  def apply(image: Mat): Mat = {
    val imageCopy = copyImage(image)
    var range: Int = maskDim / 2
    for (x <- range until imageCopy.rows() - range; y <- range until imageCopy.cols() - range) {
      var redSum = 0d
      var greenSum= 0d
      var blueSum = 0d
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
  
  private[processing] def calcColorVal(colorSum: Double): Int = {
    var color: Int = Math.round(colorSum.toFloat)
      color match {
        case c if c > 255 => 255
        case c if c < 0 => 0
        case c => c
      }
  }

}


object SharpenFilter {
  def apply(image: Mat): Mat = {
    val filter = new ConvolveFilter(Seq[Double](
    0, -1, 0, 
    -1, 5, -1, 
    0, -1, 0))
    filter(image)
  }
}

object GaussFilter {
  def apply(image: Mat): Mat = {
    val filter = new ConvolveFilter(Seq[Double](
    0.00000067, 0.00002292, 0.00019117, 0.00038771, 0.00019117, 0.00002292, 0.00000067,
    0.00002292, 0.00078634, 0.00655965, 0.01330373, 0.00655965, 0.00078633, 0.00002292,
    0.00019117, 0.00655965, 0.05472157, 0.11098164, 0.05472157, 0.00655965, 0.00019117,
    0.00038771, 0.01330373, 0.11098164, 0.22508352, 0.11098164, 0.01330373, 0.00038771,
    0.00019117, 0.00655965, 0.05472157, 0.11098164, 0.05472157, 0.00655965, 0.00019117,
    0.00002292, 0.00078633, 0.00655965, 0.01330373, 0.00655965, 0.00078633, 0.00002292,
    0.00000067, 0.00002292, 0.00019117, 0.00038771, 0.00019117, 0.00002292, 0.00002292))
    filter(image)
  }
}