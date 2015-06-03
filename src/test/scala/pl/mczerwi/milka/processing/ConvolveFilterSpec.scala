package pl.mczerwi.milka.processing

import pl.mczerwi.milka.processing.ConvolveFilter;


/**
 * @author marcin
 */
class ConvolveFilterSpec extends UnitSpec {
  
  "ConvolveFilter" should "calculate mask dimension" in {
    var lapl = new ConvolveFilter(Seq[Float](
    -1, -1, -1, 
    -1, 8, -1, 
    -1, -1, -1))
    
    lapl.maskDim shouldEqual(3)
  }
  
  it should "throw IllegalArgumentException if wrong mask is provided" in {
    a [IllegalArgumentException] should be thrownBy {
      new ConvolveFilter(Seq[Float](
        1, 2, 1, 
        2, 4, 2, 
        1, 2))
    }
  }
  
  "ConvolveFilter" should "calc correct pixel color" in {
    var lapl = new ConvolveFilter(Seq[Float](
    -1, -1, -1, 
    -1, 8, -1, 
    -1, -1, -1))
    lapl.calcColorVal(-50) shouldEqual 0
    lapl.calcColorVal(150) shouldEqual 150
    lapl.calcColorVal(1000) shouldEqual 255

  }
}