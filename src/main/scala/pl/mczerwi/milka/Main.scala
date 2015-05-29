package pl.mczerwi.milka

import org.opencv.highgui.Highgui
import pl.mczerwi.milka.MatImplicits._
import org.opencv.core.Core

/**
 * @author marcin
 */
object Main extends App with ImageDisplayer{

  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  val img = Highgui.imread("images/milka1.jpg")
  displayImage(img)
}