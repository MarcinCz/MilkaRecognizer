package pl.mczerwi.milka.processing

import org.opencv.imgproc.Imgproc
import java.awt.Color

/**
 * @author marcin
 */
case class Pixel(red: Int, green: Int, blue: Int) {
  
  private [this] lazy val hsb: Array[Float] = {
    var arrayHsb: Array[Float] = new Array[Float](3)
    Color.RGBtoHSB(red, green, blue, arrayHsb) 
    arrayHsb
  }
  
  def toRGBArray = Array[Int](blue, green, red)
   
  def hue: Double = hsb(0)
  
  def saturation: Double = hsb(1)
  
  def brightness: Double = hsb(2)
  
  def isPartOfObject:Boolean = !this.equals(BackgroundPixel())

}

object Pixel {
    def apply(array: Array[Double]) = new Pixel(array(2).toInt, array(1).toInt, array(0).toInt)
}

object DefaultObjectPixel {
  def apply() = Pixel(0, 0, 0)
}

object RandomObjectPixel {
    def apply() = {
      val r = scala.util.Random 
      Pixel(r.nextInt(254), r.nextInt(254), r.nextInt(254))
    }
}

object BackgroundPixel {
	def apply() = Pixel(255, 255, 255)
}