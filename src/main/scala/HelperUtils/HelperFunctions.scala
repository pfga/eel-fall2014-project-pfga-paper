package HelperUtils

import java.io.{InputStreamReader, BufferedReader}

import FuzzySet.AnnualRecord
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

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

  def readReduceOp(conf: Configuration, op: String) = {
    var (listLines, min, max) = (List[AnnualRecord](), 0, 0)
    try {
      val output = new Path(op)
      val fileSystem = FileSystem.get(conf)
      val bufRead = new BufferedReader(new InputStreamReader(fileSystem.open(output)))
      var line = bufRead.readLine()
      while (line != null) {

        val cols = line.split("\t")
        cols(0) match {
          case "max" => max = cols(1).toInt
          case "min" => min = cols(1).toInt
          case _ => listLines = new AnnualRecord(cols(0), cols(1).toInt) :: listLines
        }
        line = bufRead.readLine()
      }
      bufRead.close
    } catch {
      case e: Exception => println(e)
    }
    (listLines, min, max)
  }
}