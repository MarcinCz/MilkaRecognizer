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
    
    objects = getObjects("src/test/resources/leftPart/milka3.png") ++ objects
    objects.size shouldEqual(6)
    
    objects = getObjects("src/test/resources/leftPart/milka4.png") ++ objects
    objects.size shouldEqual(7)

    printImageObjectsInfo(objects)
  }
}