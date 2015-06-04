package pl.mczerwi.milka.processing

/**
 * @author marcin
 */
class RandomObjectSpec extends ImageObjectSpec {
  
  "ImageObject" should "calc invariant moments" in {
    var objects = getObjects("src/test/resources/randomObject/milka1.png")
    objects.size shouldEqual(4)
    
    objects = getObjects("src/test/resources/randomObject/milka2.png") ++ objects
    objects.size shouldEqual(7)

    printInvariantMoments(objects)
  }
}