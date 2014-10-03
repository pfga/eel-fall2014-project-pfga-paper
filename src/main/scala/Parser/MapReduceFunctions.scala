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
    values.foldLeft(0: Long) { (redOp, value) =>
      reduceAct match {
        case "min" => val v = value.get()
          if (redOp > v) v else redOp
        case "max" => val v = value.get()
          if (redOp < v) v else redOp
        case "sum" => redOp + value.get
        case _ => redOp
      }
    }
  }

  def mapFTSIp(line: T) = {
    val cols = line.toString.split("\t", -1)
    (cols(0), cols(1).toLong)
  }
}