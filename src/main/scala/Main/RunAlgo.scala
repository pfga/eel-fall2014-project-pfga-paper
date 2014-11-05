package Main

import FuzzySet.FGA
import Main.PFGAConstants._
import Parser.FTSPrepareMR.FTSDataPrepareMRDriver
import Parser.InputDataParser.DataParserMRDriver
import Parser.ParserUtils.ConfigReader
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.util.GenericOptionsParser

import scala.collection.JavaConverters._
import scala.sys.process._

/** x
  * Created by preethu19th on 10/5/14.
  */
object RunAlgo extends App {
  /*
  val conf: Configuration = ConfigReader.getConf("parse-config.properties")
  val strArgs = new GenericOptionsParser(conf, args).getRemainingArgs

  "rm -rf op".!
  val ip = "src/main/resources/410119.csv" //strArgs(0)

  val opBasePath = "op" //strArgs(1)

  val parseData = opBasePath + PARSE_DATA_PATH
  val ftsIp = opBasePath + FTS_IP_PATH
  val startTime: Long = System.nanoTime()
  DataParserMRDriver.run(conf, ip, new Path(parseData))
  FTSDataPrepareMRDriver.run(conf, parseData, new Path(ftsIp))
  System.out.println("Jobs Finished in " +
    (System.nanoTime - startTime) / NANO_SEC + " seconds")
*/
  val ftsIpFileName = "src/main/resources/input_fts_2.txt"
  //val ftsIpFileName = ftsIp + REDUCE_PART_FILENAME
  val (annualRecords, min, max, intervals) = HelperUtils.HelperFunctions
    .readReduceOp(new Configuration(), ftsIpFileName)

  println(min, max, intervals)

  //  FGA.run(400, annualRecords.asJava, 13000, 20000, 7)
//  FGA.run(1, annualRecords.asJava, min, max, 10)
}