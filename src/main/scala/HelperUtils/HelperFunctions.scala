package HelperUtils

object HelperFunctions {
  def schemaMap(schemaArr: Array[String]) = {
    schemaArr.foldLeft((Map[String, Int](), 0)) {
      (opCols, col) =>
        val (colsMap, idx) = opCols
        (colsMap + Tuple2(col, idx), idx + 1)
    }._1
  }

  def getCol(cols: Array[String], colName: String,
             schemaMap: Map[String, Int]) = {
    cols(schemaMap(colName))
  }
}