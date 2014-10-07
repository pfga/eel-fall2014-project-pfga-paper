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
   * @return
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
   * @return
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
   * @return
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


    val (retmin, retmax, intervals) = getBase(avgDist, min, max)
    (listLines, retmin, retmax, intervals)
  }

  /**
   * http://ac.els-cdn.com/S0165011400000579/1-s2.0-S0165011400000579-main.pdf?_tid=e649e7a8-4e33-11e4-9fbb-00000aab0f02&acdnat=1412694734_7320bf9dff0e8d79fc8c6253f8210bf2
   * Using average distance from above paper to find intervals
   *
   * @param avgDist
   * @param ipMin
   * @param ipMax
   * @return
   */
  def getBase(avgDist: Map[String, Int], ipMin: Int, ipMax: Int) = {
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


    var (avg, min, max) = ((sumDiff / cntDiff / 2), ipMin, ipMax)
    var redAvg = avg
    var base = 0.1

    redAvg = redAvg - 1

    while (redAvg > 0) {
      redAvg = redAvg / 10
      base = base * 10
    }

    while (avg % base != 0) avg = avg - 1
    while (min % base != 0) min = min - 1
    while (max % base != 0) max = max + 1
    (min, max, avg)
  }
}