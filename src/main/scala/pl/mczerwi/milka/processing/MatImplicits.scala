package pl.mczerwi.milka.processing

import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import org.opencv.core.Mat

/**
 * @author marcin
 */
object MatImplicits {
  implicit def mat2BufferedImage(mat: Mat) = {
    // source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
    // Fastest code
    // The output can be assigned either to a BufferedImage or to an Image

    var imgType = BufferedImage.TYPE_BYTE_GRAY
    if(mat.channels() > 1) {
      imgType = BufferedImage.TYPE_3BYTE_BGR
    }
     
    val bufferSize = mat.channels() * mat.cols() * mat.rows()
    var bytes = new Array[Byte](bufferSize)
    mat.get(0, 0, bytes)
    val image = new BufferedImage(mat.cols(), mat.rows(), imgType)
    var targetPixels = Array[Byte]()
     
    image.getRaster.getDataBuffer match {
      case dataBufferByte: DataBufferByte => targetPixels = dataBufferByte.getData
      case _ => throw new ClassCastException;
    }
     
    System.arraycopy(bytes, 0, targetPixels, 0, bytes.length)
    image
  }
}