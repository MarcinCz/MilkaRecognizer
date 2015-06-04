package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
abstract class Segmentator extends ImageProcessor {
  
  def apply(image: Mat): Mat = {
    val imageCopy = copyImage(image)
    for (x <- 0 until imageCopy.rows(); y <- 0 until imageCopy.cols()) {
      val pixel: Pixel = Pixel(image.get(x,y))
      val pixelToPut = pixel match {
        case p if matchPixel(p) => DefaultObjectPixel()
        case _ => BackgroundPixel()
      }
      putPixel(imageCopy, x, y, pixelToPut)
    }
    imageCopy  
  }
  
  def matchPixel(pixel: Pixel): Boolean
}

object PurpleSegmentator {
  def apply() = new Segmentator {
    def matchPixel(pixel: Pixel) = {
      pixel.hue >= 0.70 && pixel.hue <= 0.85 && pixel.brightness > 0.4 && pixel.brightness < 0.8 && pixel.saturation > 0.3
    }
  }
}

object WhiteSegmentator {
  def apply() = new Segmentator {
    def matchPixel(pixel: Pixel) = {
      pixel.brightness > 0.80 && pixel.saturation < 0.25
    }
  }
}