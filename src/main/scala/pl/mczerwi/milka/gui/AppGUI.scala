package pl.mczerwi.milka.gui

import scala.swing.BorderPanel
import scala.swing.Button
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.event.ButtonClicked
import pl.mczerwi.milka.processing.MatImplicits._
import java.awt.Dimension
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.FileChooser
import pl.mczerwi.milka.processing.MilkaRecognizer

/**
 * @author marcin
 */
class AppGUI extends SimpleSwingApplication {
  
  
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
    	  val fileChooserResult = fileChooser.showOpenDialog(imagePanel)
        fileChooserResult match {
          case FileChooser.Result.Approve => {
            processingStages = MilkaRecognizer(fileChooser.selectedFile.getPath)
            showProcessingStage(0)
          }
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
    mainFrame.size = new Dimension(stage.image.getWidth, stage.image.getHeight)
    currentStage = stageNumber
  }
  
  
}