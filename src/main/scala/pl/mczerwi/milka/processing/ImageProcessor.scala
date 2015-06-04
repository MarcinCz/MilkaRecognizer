package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
trait ImageProcessor {
  
  def copyImage(image: Mat): Mat = image.clone
  
  def putPixel(image:Mat, x: Int, y: Int, pixel: Pixel) = image.put(x, y, pixel.blue, pixel.green, pixel.red)
}