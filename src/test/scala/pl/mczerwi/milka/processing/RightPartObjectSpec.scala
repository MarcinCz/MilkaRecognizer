package pl.mczerwi.milka.processing

/**
 * @author marcin
 */
class RightPartObjectSpec extends ImageObjectSpec{
  
  "ImageObject" should "calc invariant moments" in {
    var objects = getObjects("src/test/resources/rightPart/milka1.png")
    objects.size shouldEqual(3)
    
    objects = getObjects("src/test/resources/rightPart/milka2.png") ++ objects
    objects.size shouldEqual(4)

    printInvariantMoments(objects)
  }
}