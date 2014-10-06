import java.io.{InputStreamReader, BufferedReader}

import FuzzySet.FGA
import Parser.FTSPrepareMR.FTSDataPrepareMRDriver
import Parser.InputDataParser.DataParserMRDriver
import Parser.ParserUtils.ConfigKeyNames._
import Parser.ParserUtils.ConfigReader
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.util.GenericOptionsParser
import scala.collection.JavaConverters._

import Constants._

/**
 * Created by preethu19th on 10/5/14.
 */
object RunDataParser extends App {

  val conf: Configuration = new Configuration()
  val strArgs = new GenericOptionsParser(conf, args).getRemainingArgs
  /*  val ip = strArgs(1)

    val parseData = strArgs(2) + parseDataPath
    val ftsIp = strArgs(2) + ftsIpPath
    val parseConfig = strArgs(3)

    val config = ConfigReader.getConfig("parse-config.properties")

    val schemaArr = config.getStringArray(schema)
    val dtKeyName = config.getString(loadDate)
    val reduceColName = config.getString(reduceColumn)
    val reduceAct = config.getString(reduceAction)
    val delimiter = config.getString(delimiterStr)

    conf.set(schema, schemaArr.mkString(","))
    conf.set(loadDate, dtKeyName)
    conf.set(reduceColumn, reduceColName)
    conf.set(reduceAction, reduceAct)
    conf.set(delimiterStr, delimiter)


    //DataParserMRDriver.run(conf, ip, new Path(parseData))

    //  FTSDataPrepareMRDriver.run(conf, parseData, new Path(ftsIp))
  */

  val (annualRecords, min, max) = HelperUtils.HelperFunctions
    .readReduceOp(conf, "op" + ftsIpPath + reducePartFileName)
  FGA.run(annualRecords.asJava, 400, 1200)
}
