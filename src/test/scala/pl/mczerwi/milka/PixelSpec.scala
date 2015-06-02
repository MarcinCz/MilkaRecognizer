package pl.mczerwi.milka

import org.scalatest.junit.JUnitRunner

/**
 * @author marcin
 */
class PixelSpec extends UnitSpec {
  
  "Pixel" should "return correct HSB values" in {
    val pixel = new Pixel(1,2,3)
    pixel.hue should be (0.58 +- 1e-2)
    pixel.saturation should be (0.66 +- 1e-2)
    pixel.brightness should be (0.012 +- 1e-3)
  }
}