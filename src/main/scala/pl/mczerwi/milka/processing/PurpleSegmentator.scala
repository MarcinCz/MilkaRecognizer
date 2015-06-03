package pl.mczerwi.milka.processing

import org.opencv.core.Mat

import pl.mczerwi.milka.processing.ImageProcessor;
import pl.mczerwi.milka.processing.Pixel;

/**
 * @author marcin
 */
class PurpleSegmentator extends ImageProcessor {
  
  def apply(image: Mat): Mat = {
    val imageCopy = copyImage(image)
    for (x <- 0 until imageCopy.rows(); y <- 0 until imageCopy.cols()) {
      val pixel: Pixel = Pixel(image.get(x,y))
      val pixelToPut = pixel match {
        case p if (p.hue >= 0.75 && p.hue <= 0.8) => new Pixel(0, 0, 0)
        case _ => new Pixel(255, 255, 255)
      }
      putPixel(imageCopy, x, y, pixelToPut)
    }
    imageCopy  
  }
}