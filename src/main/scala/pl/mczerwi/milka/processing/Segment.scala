package pl.mczerwi.milka.processing

import org.opencv.core.Mat

/**
 * @author marcin
 */
case class Segment(bounds: Bounds, image: Mat)

case class Bounds(minX: Int, maxX: Int, minY: Int, maxY: Int)