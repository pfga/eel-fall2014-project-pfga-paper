package Parser

import ParserUtils.{ConfigReader, ConfigKeyNames}

import scala.collection.JavaConversions._
import HelperUtils.HelperFunctions
import ConfigKeyNames._
import org.apache.hadoop.io.{LongWritable => LW, Text => T}

/**
 * Created by preethu19th on 9/25/14.
 */
class MapReduceFunctions(configFileName: String) {
  lazy val config = ConfigReader.getConfig(configFileName)
  lazy val schemaArr = config.getStringArray(schema)

  lazy val dtKeyName = config.getString(loadDate)
  lazy val reduceColName = config.getString(reduceColumn)
  lazy val reduceAct = config.getString(reduceAction)
  lazy val delimiter = config.getString(delimiterStr)
  lazy val schemaMap = HelperFunctions.schemaMap(schemaArr)

  def mapRawLine(line: T) = {
    val cols = line.toString.split(delimiter, -1)
    (HelperFunctions.getCol(cols, dtKeyName, schemaMap),
      HelperFunctions.getCol(cols, reduceColName, schemaMap).toLong)
  }

  def reduceRawLine(values: java.lang.Iterable[LW],
                    currRedAct: String = reduceAct) = {
    values.foldLeft((0: Long, true)) { (opCols, value) =>
      val v = value.get()
      val (redOp, firstBool) = opCols
      val newRedOp = if (firstBool) {
        v
      } else {
        reduceAct match {
          case "min" => if (redOp < v) v else redOp
          case "max" => if (redOp > v) v else redOp
          case "sum" => redOp + v
          case _ => redOp
        }
      }
      (newRedOp, false)
    }._1
  }

  def mapFTSIp(line: T) = {
    val cols = line.toString.split("\t", -1)
    (cols(0), cols(1).toLong)
  }
}