package pl.mczerwi.milka.processing

import org.opencv.core.Mat
import scala.collection.mutable.ListBuffer

/**
 * @author marcin
 */
abstract class Identificator extends ImageProcessor {
 
  def apply(image: Mat, objects: Seq[ImageObject]): IdentificatorResult = {
    val imgCopy = copyImage(image)
    val identifiedObjects = new ListBuffer[ImageObject]
    for(imgObject <- objects) {
      if(checkConstraints(imgObject)) {
        identifiedObjects += imgObject
      } else {
        removeObject(imgCopy, imgObject)
      }
    }
    
    IdentificatorResult(imgCopy, identifiedObjects)
  }
  
  protected def checkConstraints(imageObject: ImageObject): Boolean

  def removeObject(image: Mat, imgObject: ImageObject) = {
    for (position <-imgObject.pixelPositions) {
      putPixel(image, position.x, position.y, BackgroundPixel())
    }
  }
}

object LeftPartIdentificator {
  def apply(): Identificator = {
    new Identificator {
      def checkConstraints(imageObject: ImageObject): Boolean = {
        imageObject.M1 > 0.25 && imageObject.M1 < 0.4
        imageObject.M2 > 0.001 && imageObject.M2 < 0.05 &&
        imageObject.M7 > 0.015 && imageObject.M7 < 0.04
      }
    }
  }
}

object RightPartIdentificator {
  def apply(): Identificator = {
    new Identificator {
      def checkConstraints(imageObject: ImageObject): Boolean = {
        imageObject.M1 > 0.3 && imageObject.M1 < 0.5
        imageObject.M2 > 0.02 && imageObject.M2 < 0.2 &&
        imageObject.M7 > 0.01 && imageObject.M7 < 0.025
      }
    }
  }
}

case class IdentificatorResult(image: Mat, identifiedObjects: Seq[ImageObject])