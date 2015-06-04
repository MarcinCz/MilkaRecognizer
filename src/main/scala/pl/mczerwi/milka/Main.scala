package pl.mczerwi.milka

import org.opencv.core.Core
import org.opencv.highgui.Highgui
import pl.mczerwi.milka.processing.MatImplicits._
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.ImageProcessingStage
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.gui.AppGUI
import pl.mczerwi.milka.processing.MedianFilter

/**
 * @author marcin
 */
object Main extends App {

  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

  AppGUI.startup(args)

}