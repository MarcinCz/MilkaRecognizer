package pl.mczerwi.milka.gui

import java.awt.Dimension

import scala.swing.BorderPanel
import scala.swing.BoxPanel
import scala.swing.Button
import scala.swing.FileChooser
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Orientation
import scala.swing.SimpleSwingApplication
import scala.swing.event.ButtonClicked

import org.slf4j.LoggerFactory

import com.typesafe.scalalogging.Logger

import pl.mczerwi.milka.processing.MilkaRecognizer

/**
 * @author marcin
 */
object AppGUI extends SimpleSwingApplication {
  
  private val logger = Logger(LoggerFactory.getLogger(getClass.getName))

  def top = mainFrame
  
  private def buttonPanel = new BoxPanel(Orientation.Horizontal) {
    val buttonRight = new Button("Next")
    val buttonLeft = new Button("Previous")
    val buttonFileSelect = new Button("Select file")
    
    contents += buttonFileSelect
    contents += buttonLeft
    contents += buttonRight
    
    listenTo(buttonFileSelect, buttonRight, buttonLeft)
    reactions += {
      case ButtonClicked(`buttonLeft`) => 
        if(currentStage > 0) {
          showProcessingStage(currentStage - 1)
        }
      case ButtonClicked(`buttonRight`) => 
        if(currentStage < processingStages.size - 1) {
          showProcessingStage(currentStage + 1)
        }
      case ButtonClicked(`buttonFileSelect`) => {
        fileChooser.showOpenDialog(imagePanel) match {
          case FileChooser.Result.Approve => {
            try {
            	processingStages = MilkaRecognizer(fileChooser.selectedFile.getPath)              
            } catch {
              case e : Throwable => {
                titleLabel.text = "Error while reading file"
                logger.error("Error while reading image file", e)
              } 
            }
            showProcessingStage(0)
          }
          case _ =>
        }
      }
    }
  }
  
  private val titleLabel = new Label {
    text = "Select file"
  }
  
  private val imagePanel: ImagePanel = new ImagePanel {
  }
  
  private val fileChooser: FileChooser = new FileChooser
  
  private val mainFrame = new MainFrame {
    title = "Milka Recognizer"
    
    contents = new BorderPanel {
      layout(titleLabel) = BorderPanel.Position.North
      layout(imagePanel) = BorderPanel.Position.Center
      layout(buttonPanel) = BorderPanel.Position.South
    }
  }
  
  private var processingStages:Seq[ImageProcessingStage] = Nil
  private var currentStage: Int = 0

  
  private def showProcessingStage(stageNumber: Int) {
    val stage = processingStages(stageNumber)
    titleLabel.text = stage.title
    imagePanel.image = stage.image
    mainFrame.size = new Dimension(stage.image.getWidth + 20, stage.image.getHeight + 80)
    currentStage = stageNumber
  }
  
  
}