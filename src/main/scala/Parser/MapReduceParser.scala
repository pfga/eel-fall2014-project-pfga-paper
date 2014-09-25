package Parser

import HelperUtils.HelperFunctions
import Parser.ConfigKeyNames._
import org.apache.hadoop.io.Text

/**
 * Created by preethu19th on 9/25/14.
 */
object MapReduceParser {
  val config = ConfigReader.getConfig("parse-config.properties")
  val schemaArr = config.getStringArray(schema)

  val dtKeyName = config.getString(loadDate)
  val reduceColName = config.getString(reduceColumn)
  val reduceAct = config.getString(reduceAction)
  val delimiter = config.getString(delimiterStr)
  val schemaMap = HelperFunctions.schemaMap(schemaArr)

  def mapLine(line: String) = {
    val cols = line.split(delimiter, -1)

    (HelperFunctions.getCol(cols, dtKeyName, schemaMap),
      HelperFunctions.getCol(cols, reduceColName, schemaMap).toInt)
  }

  def reduceLine(values : List[Int]) = {
    values.foldLeft(0) { (redOp, value) =>
      reduceAct match {
        case "sum" => redOp + value
        case _ => redOp
      }
    }
  }

}
