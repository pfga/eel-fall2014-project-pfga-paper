import HelperUtils.HelperFunctions
import Parser.{ConfigReader, SchemaValidator}
import Parser.ConfigKeyNames._

/**
 * Created by preethu on 9/22/14.
 */
object RunParser extends App {

  val config = ConfigReader.getConfig("parse-config.properties")
  if (!config.isEmpty) {
    runCode()
  } else {
    println("Invalid config")
  }

  def runCode() = {
    val config = ConfigReader.getConfig("parse-config.properties")
    val schemaArr = config.getStringArray(schema)
    if (!SchemaValidator.checkSchema(schemaArr)) {
      println("Exiting")
    }

    val dtKeyName = config.getString(loadDate)
    val reduceColName = config.getString(reduceColumn)
    val delimiter = config.getString(delimiterStr)
    val schemaMap = HelperFunctions.schemaMap(schemaArr)

    val mapIpLines = io.Source.fromURL(getClass.getResource("/input.txt")).getLines()
    val mapOps = mapIpLines.foldLeft(Map[String, List[Int]]()) { (mapOp, line) =>
      val cols = line.split(delimiter, -1)

      val key = HelperFunctions.getCol(cols, dtKeyName, schemaMap)
      val value = HelperFunctions.getCol(cols, reduceColName, schemaMap).toInt
      val opList = if (mapOp.contains(key)) {
        value :: mapOp(key)
      } else List(value)
      mapOp + Tuple2(key, opList)
    }

    mapOps.foreach { mapOpLine =>
      val (key, values) = mapOpLine
      val redOp = values.foldLeft(0) { (redOp, value) =>
        redOp + value
      }
      println(key + "_" + redOp)
    }
  }
}