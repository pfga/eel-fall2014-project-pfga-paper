package HelperUtils

import java.io.{InputStreamReader, BufferedReader}

import FuzzySet.AnnualRecord
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

object HelperFunctions extends App {
  /**
   * Convert schema into a Map which will provide index based on column name
   *
   * @param schemaArr
   * @return schemaMap: Map[String,Int]
   */
  def schemaMap(schemaArr: Array[String]) = {
    schemaArr.foldLeft((Map[String, Int](), 0)) {
      (opCols, col) =>
        val (colsMap, idx) = opCols
        (colsMap + Tuple2(col, idx), idx + 1)
    }._1
  }

  /**
   * Use the cols, colName and schemaMap
   * to find the required column value based
   * on column name.
   *
   * @param cols
   * @param colName
   * @param schemaMap
   * @return colVal:String
   */

  def getCol(cols: Array[String], colName: String,
             schemaMap: Map[String, Int]) = {
    cols(schemaMap(colName))
  }

  /**
   * Read the time data from HDFS from last MapReduce job
   * Along with min,max and the time data, find interval
   *
   * @param conf
   * @param opStr
   * @return (annualRecords:List[AnnualRecords],min:Int,max:Int,intervals:Int)
   */
  def readReduceOp(conf: Configuration, opStr: String) = {
    var avgDist = Map[String, Int]()
    var (listLines, min, max) = (List[AnnualRecord](), 0, 0)
    try {
      val op = new Path(opStr)
      val fs = FileSystem.get(conf)
      val bufRead = new BufferedReader(new InputStreamReader(fs.open(op)))

      var line = bufRead.readLine()
      while (line != null) {
        if (!line.trim.isEmpty) {
          val cols = line.split("\t")
          cols(0) match {
            case "max" => max = cols(1).toInt
            case "min" => min = cols(1).toInt
            case _ =>
              avgDist = avgDist + Tuple2(cols(0), cols(1).toInt)
              listLines = new AnnualRecord(cols(0), cols(1).toInt) :: listLines
          }
        }
        line = bufRead.readLine()
      }
      bufRead.close
    } catch {
      case e: Exception => println(e)
    }


    val (retmin, retmax, intervals) = getRoundedIntervalMinMax(avgDist, min, max)
    (listLines, retmin, retmax, intervals)
  }

  /**
   * http://www.sciencedirect.com/science/article/pii/S0165011400000579
   * Using average distance from above paper to find intervals
   *
   * @param avgDist
   * @param ipMin
   * @param ipMax
   * @return (min:Int,max:Int,intervals:Int)
   */
  def getRoundedIntervalMinMax(avgDist: Map[String, Int], ipMin: Int, ipMax: Int) = {
    val dflVal = (0, 0, "")

    val (sumDiff, cntDiff, prevKey) = avgDist.keys
      .toArray.sorted.foldLeft(dflVal) { (op, key) =>
      val (sumDiff, cntDiff, prevKey) = op
      if (prevKey.isEmpty)
        (sumDiff, cntDiff, key)
      else {
        val diff = Math.abs(avgDist(key) - avgDist(prevKey))
        (sumDiff + diff, cntDiff + 1, key)
      }
    }

    val avgVal = (sumDiff / cntDiff / 2)
    val base = getBase(avgVal)

    val avg = getRoundedToBase(avgVal, -1, base)
    val min = getRoundedToBase(ipMin, -1, base)
    val max = getRoundedToBase(ipMax, 1, base)

    (min, max, avg)
  }

  /**
   *
   * @param value
   * @param addVal
   */
  def getRoundedToBase(value: Int, addVal: Int, base: Double) = {
    var rndVal = value
    while (rndVal % base != 0) rndVal = rndVal + addVal
    rndVal
  }

  /**
   * Find base using half of average
   *
   * @param avg
   * @return base:Double
   */
  def getBase(avg: Int): Double = {
    var redAvg = avg - 1
    var base = 0.1

    while (redAvg > 0) {
      redAvg = redAvg / 10
      base = base * 10
    }
    base
  }
}