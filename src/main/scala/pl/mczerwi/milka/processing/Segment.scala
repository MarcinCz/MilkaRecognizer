package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
case class Segment(bounds: Bounds, pixelsPositions: Seq[PixelPosition])

case class Bounds(minX: Int, maxX: Int, minY: Int, maxY: Int)

case class PixelPosition(x: Int, y: Int)

