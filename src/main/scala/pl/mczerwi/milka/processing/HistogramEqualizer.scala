package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
class HistogramEqualizer extends ImageProcessor {
 
  type PixelToColor = Pixel => Int
  
  def apply(image: Mat): Mat = {
    val imgCopy = copyImage(image)
    var pixels = getPixels(imgCopy)
    
    val composedEqualizers = equalizeBlue _ compose equalizeRed _
    pixels = composedEqualizers(pixels)
    for(x <- 0 until imgCopy.rows; y <- 0 until imgCopy.cols) {
      putPixel(imgCopy, x, y, pixels(x * imgCopy.cols + y))
    }
    imgCopy
  }
  
  private[this] def equalizeRed(pixels: Seq[Pixel]) = {
    val lookupTable = createLookupTable(pixels, { p => p.red })
	  val newPixels = for(i <- 0 until pixels.size) yield pixels(i).copy(red = lookupTable(pixels(i).red))
    newPixels
  }
  
  private[this] def equalizeBlue(pixels: Seq[Pixel]) = {
    val lookupTable = createLookupTable(pixels, { p => p.blue })
    for(i <- 0 until pixels.size) yield pixels(i).copy(blue = lookupTable(pixels(i).blue))
  }
   
  private[this] def equalizeGreen(pixels: Seq[Pixel]) = {
    val lookupTable = createLookupTable(pixels, { p => p.green })
    for(i <- 0 until pixels.size) yield pixels(i).copy(green = lookupTable(pixels(i).green))
  }

  
  private[this] def createLookupTable(pixels: Seq[Pixel], pixelToColor: PixelToColor): Seq[Int] = {
    val histogram = new Array[Int](256)
    for(value <- 0 until histogram.length)
      histogram.update(value, pixels.count(pixelToColor(_) == value))
    
    val distribution = for(
      index <- 0 until histogram.length
    ) yield histogram.take(index + 1).sum
    
    val cdfMin = distribution.find { value => value != 0 }.get
    val histogramSum = histogram.sum
    val lookupTable = distribution.map { cdf => ((cdf - cdfMin)* 255) / (histogramSum - cdfMin)  }

    lookupTable
  }
  
  private[this] def getPixels(image: Mat) = {
    for(
      x <- 0 until image.rows;
      y <- 0 until image.cols
    ) yield Pixel(image.get(x, y))
  }
}

object HistogramEqualizer {
  def apply() = new HistogramEqualizer
}