package pl.mczerwi.milka.processing

import org.opencv.core.Mat
import scala.collection.mutable.Stack
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer

/**
 * @author marcin
 */
class ImageObjectSplitter extends ImageProcessor {
  def apply(image: Mat, minSegmentSize: Int) = {
    val imgCopy = copyImage(image)
    val objects = splitObjects(imgCopy, minSegmentSize)
    for(imgObject <- objects) {
      val colorPixel = imgObject.color
      for(position <- imgObject.pixelPositions) {
        putPixel(imgCopy, position.x, position.y, colorPixel)
      }
    }
    new ImageObjectSplitterResult(objects, imgCopy)
  }
  
  private[this] def splitObjects(img: Mat, minSegmentSize: Int) = {
    val objects = new ListBuffer[ImageObject]
    
    for( x <- 0 until img.rows(); y <- 0 until img.cols()) {
      val pixelPosition = new PixelPosition(x, y)
      if(isPartOfObject(img, pixelPosition.x, pixelPosition.y)) {
         val pixels = new ListBuffer[PixelPosition]  //all pixels in segment
         val pending = new Stack[PixelPosition] // pending pixels, which neighbors need to be checked
         pixels += pixelPosition
         pending.push(pixelPosition)
         
         while(!pending.isEmpty) {
           val pendingPixelPosition = pending.pop()
           if(isPartOfObject(img, pendingPixelPosition.x, pendingPixelPosition.y)) {
             putPixel(img, pendingPixelPosition.x, pendingPixelPosition.y, BackgroundPixel())
             pixels += pendingPixelPosition
               if(pendingPixelPosition.x > 0 && isPartOfObject(img, pendingPixelPosition.x - 1, pendingPixelPosition.y)) 
                 pending.push(new PixelPosition(pendingPixelPosition.x - 1, pendingPixelPosition.y))
               if(pendingPixelPosition.x < img.rows - 1 && isPartOfObject(img, pendingPixelPosition.x + 1, pendingPixelPosition.y)) 
                 pending.push(new PixelPosition(pendingPixelPosition.x + 1, pendingPixelPosition.y))
               if(pendingPixelPosition.y > 0 && isPartOfObject(img, pendingPixelPosition.x, pendingPixelPosition.y -1)) 
                 pending.push(new PixelPosition(pendingPixelPosition.x, pendingPixelPosition.y - 1))
               if(pendingPixelPosition.y < img.cols -1 && isPartOfObject(img, pendingPixelPosition.x, pendingPixelPosition.y + 1)) 
                 pending.push(new PixelPosition(pendingPixelPosition.x, pendingPixelPosition.y + 1))
           }
         }
         
         if(pixels.size > minSegmentSize) {
           objects += createImageObject(pixels)
         }
      }
    }
    objects
  }
  
  private[this] def isPartOfObject(img: Mat, x: Int, y: Int) = Pixel(img.get(x, y)).isPartOfObject

  private[this] def createImageObject(pixelPositions: Seq[PixelPosition]) = {
    var minX: Int = Int.MaxValue
    var maxX: Int = Int.MinValue
    var minY: Int = Int.MaxValue
    var maxY: Int = Int.MinValue
    
    for (position <- pixelPositions) {
      minX = Math.min(minX, position.x)
      maxX = Math.max(maxX, position.x)
      minY = Math.min(minY, position.y)
      maxY = Math.max(maxY, position.y)
    }
    
    new ImageObject(new Bounds(minX, maxX, minY, maxY), pixelPositions, RandomObjectPixel())
  }
}

object ImageObjectSplitter {
  def apply() = new ImageObjectSplitter
}

case class ImageObjectSplitterResult(objects: Seq[ImageObject], image: Mat)