package Parser

import scala.collection.JavaConversions._
import HelperUtils.HelperFunctions
import Parser.ConfigKeyNames._
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

  def mapLine(line: T) = {
    val cols = line.toString.split(delimiter, -1)

    (HelperFunctions.getCol(cols, dtKeyName, schemaMap),
      HelperFunctions.getCol(cols, reduceColName, schemaMap).toLong)
  }

  def reduceLine(values: java.lang.Iterable[LW]) = {

    values.foldLeft(0: Long) { (redOp, value) =>
      reduceAct match {
        case "sum" => redOp + value.get
        case _ => redOp
      }
    }
  }
}