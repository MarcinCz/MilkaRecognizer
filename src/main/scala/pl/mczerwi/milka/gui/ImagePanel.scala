package pl.mczerwi.milka.gui

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.awt.Graphics2D
import scala.swing.Panel
import java.io.File
import java.awt.Dimension

/**
 * @author marcin
 */

abstract class ImagePanel extends Panel                                                
{
  private var _image: BufferedImage = null
  
  def image: BufferedImage = _image
  def image_=(image: BufferedImage) = {
   _image = image
   repaint()
  }
  
  def size_=(dimension: Dimension) = {
    self.setSize(dimension)
  }

  override def paintComponent(g:Graphics2D) =                                 
  {                                                                           
    if (null != _image) g.drawImage(_image, 0, 0, null)         
  }                                                                           
}                                                                             
