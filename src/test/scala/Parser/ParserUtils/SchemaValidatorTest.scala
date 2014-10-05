package Parser.ParserUtils

import org.scalatest.FlatSpec

import scala.collection.Set

/**
 * Created by preethu on 9/22/14.
 */


class SchemaValidatorTest extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  "Schema" should "have 'load-date' column" in {
    assert(SchemaValidator.checkSchema(Array("load-date")))
    assert(!SchemaValidator.checkSchema(Array("")))
  }
  /*
  test("Invoking head on an empty Set should produce NoSuchElementException") {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  */
}
