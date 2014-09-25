package HelperUtils

import org.scalatest.FlatSpec

class HelperFunctionsTest extends FlatSpec {
  "HelperFunctions.schemaMap" should "return appropriate Map" in {
    val testSchema = Array("1", "2", "3", "4")
    val schemaMapVal = HelperFunctions.schemaMap(testSchema)
    for ((k, v) <- schemaMapVal)
      assert(k.toInt == v + 1)
  }

  "HelperFunctions.getCol" should "return appropriate colVal for colName" in {
    val testSchema = Array("1", "2", "3", "4")
    val schemaMapVal = HelperFunctions.schemaMap(testSchema)
    for ((k, v) <- schemaMapVal)
      assert(k == HelperFunctions.getCol(testSchema,k,schemaMapVal))
  }
}