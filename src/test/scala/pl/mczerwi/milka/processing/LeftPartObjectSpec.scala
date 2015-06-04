package pl.mczerwi.milka.processing

/**
 * @author marcin
 */
class LeftPartObjectSpec extends ImageObjectSpec {
  
  "ImageObject" should "calc invariant moments" in {
    var objects = getObjects("src/test/resources/leftPart/milka1.png")
		objects.size shouldEqual(3)
    
    objects = getObjects("src/test/resources/leftPart/milka2.png") ++ objects
    objects.size shouldEqual(4)

    printInvariantMoments(objects)
  }
}