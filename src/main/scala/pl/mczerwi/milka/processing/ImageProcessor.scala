package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
abstract class ImageProcessor {
  def apply(image: Mat): Mat
  
  def copyImage(image: Mat): Mat = image.clone
  
  def putPixel(image:Mat, x: Int, y: Int, pixel: Pixel) = image.put(x, y, pixel.blue, pixel.green, pixel.red)
}