package pl.mczerwi.milka.processing

import org.opencv.core.Mat
import org.scalatest.BeforeAndAfter
import org.opencv.core.Core
import org.opencv.highgui.Highgui

/**
 * @author marcin
 */
abstract class ImageObjectSpec extends UnitSpec with BeforeAndAfter  {
  
  
  def fixture = new {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    val splitter: ImageObjectSplitter = ImageObjectSplitter()
  }
  
  def getObjects(fileName: String): Seq[ImageObject] = {
    fixture.splitter(Highgui.imread(fileName), 5).objects
  }
  
  def printImageObjectsInfo(imageObjects: Seq[ImageObject]) {
    def printLine(name: String, f: ImageObject => Any) = {
      print(name)
      for (obj <- imageObjects)
    	  print("\t" + f(obj))
      println
    }

    printLine("area", obj => obj.area)
    printLine("center", obj => obj.center)
    printLine("M1", obj => obj.M1)
    printLine("M2", obj => obj.M2)
    printLine("M3", obj => obj.M3)
    printLine("M4", obj => obj.M4)
    printLine("M5", obj => obj.M5)
    printLine("M6", obj => obj.M6)
    printLine("M7", obj => obj.M7)
    printLine("M8", obj => obj.M8)
    printLine("M9", obj => obj.M9)
    printLine("M10", obj => obj.M10)
  }
}