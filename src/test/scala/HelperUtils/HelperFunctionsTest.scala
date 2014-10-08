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
      assert(k == HelperFunctions.getCol(testSchema, k, schemaMapVal))
  }

  "HelperFunctions.getBase" should "return appropriate base for int value" in {
    val testStrs = Array((1, 0.1), (2, 1.0), (10, 1.0), (11, 10.0), (100, 10.0),
      (101, 100.0), (1000, 100.0), (1001, 1000.0), (9999, 1000.0), (10000, 1000.0))
    for ((value, base) <- testStrs) {

      assert(base == HelperFunctions.getBase(value))
    }
  }
/*
  "HelperFunctions.getRoundedToBase" should "returns whole number" in {
    val testStrs = Array((44, -1, 40), (44, 1, 50))
    for ((ip, add, op) <- testStrs) {
      assert(op == HelperFunctions.getRoundedToBase(ip, add))
    }
  }
*/
  "HelperFunctions.getRoundedIntervalMinMax" should "returns min, max and interval" in {
    val testMap = Map("1970" -> 11, "1971" -> 22, "1972" -> 33)
    assert((10, 40, 5) == HelperFunctions.getRoundedIntervalMinMax(testMap, 11, 33))
  }
}