package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
class FullLogoFinder extends ImageProcessor {
   
  def apply(image: Mat, purpleObjects: Seq[ImageObject], leftPartObjects: Seq[ImageObject], rightPartObjects: Seq[ImageObject]): Mat = {
    val imgCopy = copyImage(image)
    
    for(leftPart <- leftPartObjects) {
      var found = false
      for(rightPart <- rightPartObjects if !found) {
        if(checkParts(leftPart, rightPart)) {
          purpleObjects.find(obj => leftPart.isInside(obj) && rightPart.isInside(obj)) match {
            case Some(obj) => {
              markLogo(imgCopy, leftPart.bounds + rightPart.bounds)
              found = true
            }
            case None =>
          }
        }
      }
    }
    
    imgCopy
  }
  
  private[this] def checkParts(leftPart: ImageObject, rightPart: ImageObject): Boolean = {
    if(leftPart == rightPart) 
      return false

    val areaRatio = rightPart.area / leftPart.area
    if(areaRatio > 1.2 || areaRatio < 0.8) 
      return false
    
    val distanceBetweenCentersSquared = Math.pow(leftPart.center distanceTo rightPart.center, 2)
    val minArea = Math.min(leftPart.area, rightPart.area)
    if(distanceBetweenCentersSquared / minArea > 4.4 || distanceBetweenCentersSquared / minArea < 2.2) 
      return false
      
    true
  }
  
  private[this] def markLogo(image: Mat, bounds: Bounds) {
    for {
      x <- bounds.minX to bounds.maxX; y <- bounds.minY to bounds.maxY
      if x == bounds.minX || x == bounds.maxX || y == bounds.minY || y == bounds.maxY
    } putPixel(image, x, y, Pixel(0, 255, 0))
  }
}

object FullLogoFinder {
  def apply(): FullLogoFinder = new FullLogoFinder
}