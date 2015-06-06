package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
case class ImageObject(bounds: Bounds, pixelPositions: Seq[PixelPosition], color: Pixel) {
   
  private[this] def calcNormalMoment(p: Int, q: Int) = {
    var sum: Double = 0
    for(position <- pixelPositions) {
      sum += Math.pow(position.x, p) * Math.pow(position.y, q)
    }
    sum
  }
  
  override def equals(other: Any) = {
    other match {
      case otherObject: ImageObject => bounds == otherObject.bounds && pixelPositions.sameElements(otherObject.pixelPositions)
      case _ => false
    }
  }
  
  def isInside(other: ImageObject) = {
    other.bounds.minX < bounds.minX && other.bounds.maxX > bounds.maxX &&
    other.bounds.minY < bounds.minY && other.bounds.maxY > bounds.maxY
  }
  
  def area = m00
  def center = PixelPosition(iCenter.toInt, jCenter.toInt)
  
  lazy val m00 = calcNormalMoment(0, 0)
  lazy val m01 = calcNormalMoment(0, 1)
  lazy val m02 = calcNormalMoment(0, 2)
  lazy val m03 = calcNormalMoment(0, 3)
  lazy val m10 = calcNormalMoment(1, 0)
  lazy val m11 = calcNormalMoment(1, 1)
  lazy val m12 = calcNormalMoment(1, 2)
  lazy val m20 = calcNormalMoment(2, 0)
  lazy val m21 = calcNormalMoment(2, 1)
  lazy val m30 = calcNormalMoment(3, 0)

  lazy val iCenter = m10/m00
  lazy val jCenter = m01/m00
  
  lazy val Mc00 = m00
  lazy val Mc02 = m02 - Math.pow(m01, 2) / m00
  lazy val Mc03 = m03 - 3 * m02 * jCenter + 2 * m01 * Math.pow(jCenter, 2)
  lazy val Mc11 = m11 - m10 * m01 / m00
  lazy val Mc12 = m12 - 2 * m11 * jCenter - m02 * iCenter + 2 * m10 * Math.pow(jCenter, 2)
  lazy val Mc20 = m20 - Math.pow(m10, 2) / m00
  lazy val Mc21 = m21 - 2 * m11 * jCenter - m20 * jCenter + 2 * m01 * Math.pow(iCenter, 2)
  lazy val Mc30 = m30 - 3 * m02 * jCenter + 2 * m10 * Math.pow(iCenter, 2)
  
  lazy val M1 = (Mc20 + Mc02) / Math.pow(m00, 2)
  lazy val M2 = (Math.pow(Mc20 - Mc02, 2) + 4 * Math.pow(Mc11, 2)) / Math.pow(m00, 4)
  lazy val M3 = (Math.pow(Mc30 - 3 * Mc12, 2) + Math.pow(3 * Mc21 - Mc03, 2)) / Math.pow(m00, 5)
  lazy val M4 = (Math.pow(Mc30 + Mc12, 2) + Math.pow(Mc21 + Mc03, 2)) / Math.pow(m00, 5)
  lazy val M5 = ((Mc30 - 3 * Mc12) * (Mc30 + Mc12) * (Math.pow(Mc30 + Mc12, 2) - 3 * Math.pow(Mc21 + Mc03, 2)) + 
      + (3 * Mc21 - Mc03) * (Mc21 + Mc03) * (3 * Math.pow(Mc30 + Mc12, 2) - Math.pow(Mc21 + Mc03, 2))) / Math.pow(m00, 10)
  lazy val M6 = ((Mc20 - Mc02) * (Math.pow(Mc30 + Mc12, 2) - Math.pow(Mc21 + Mc03, 2) + 4 * Mc11 * (Mc30 + Mc12) * (Mc21 + Mc03))) / Math.pow(m00, 7)
  lazy val M7 = ((Mc20 * Mc02 - Math.pow(Mc11, 2))) / Math.pow(m00, 4)
  lazy val M8 = (Mc30 * Mc12 + Mc21 * Mc03 - Math.pow(Mc12, 2) - Math.pow(Mc21, 2)) / Math.pow(m00, 5)
  lazy val M9 = (Mc20 * (Mc21 * Mc03 - Math.pow(Mc12, 2)) + Mc02 * (Mc03 * Mc12 - math.pow(Mc21, 2)) - 
      - Mc11 * (Mc30 * Mc03 - Mc21 * Mc12)) / Math.pow(m00, 7)
  lazy val M10 = (Math.pow(Mc30 * Mc03 - Mc12 * Mc21, 2) - 4 * ( Mc30 * Mc12 - Math.pow(Mc21, 2) * (Mc03 * Mc21 - Mc12))) / Math.pow(m00, 10)
}

case class Bounds(minX: Int, maxX: Int, minY: Int, maxY: Int) {
  def +(other: Bounds): Bounds = {
    Bounds(
      Math.min(minX, other.minX),
      Math.max(maxX, other.maxX),
      Math.min(minY, other.minY),
      Math.max(maxY, other.maxY))
  }
}

case class PixelPosition(x: Int, y: Int) {
  def distanceTo(other: PixelPosition) = {
    Math.sqrt(Math.pow(x - other.x, 2) +Math.pow(y - other.y,2))
  }
}
