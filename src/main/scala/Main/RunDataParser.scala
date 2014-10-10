package Main

import FuzzySet.FGA
import Parser.FTSPrepareMR.FTSDataPrepareMRDriver
import Parser.InputDataParser.DataParserMRDriver
import Parser.ParserUtils.ConfigReader
import Main.PFGAConstants._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.util.GenericOptionsParser

import scala.collection.JavaConverters._

/**
 * Created by preethu19th on 10/5/14.
 */
object RunDataParser extends App {

  val conf: Configuration = ConfigReader.getConf("parse-config.properties")
  val strArgs = new GenericOptionsParser(conf, args).getRemainingArgs
  val ip = strArgs(0)

  val parseData = strArgs(1) + PARSE_DATA_PATH
  val ftsIp = strArgs(1)+ FTS_IP_PATH
  val startTime: Long = System.nanoTime()

  DataParserMRDriver.run(conf, ip, new Path(parseData))
  FTSDataPrepareMRDriver.run(conf, parseData, new Path(ftsIp))
  System.out.println("Job Finished in " +
    (System.nanoTime - startTime) / NANO_SEC + " seconds")
/*
  val (annualRecords, min, max, intervals) = HelperUtils.HelperFunctions
    .readReduceOp(new Configuration(), "src/main/resources/input_fts_1.txt")

  println(min + "_" + max + "_" + intervals)
  FGA.run(1000, annualRecords.asJava, min, max, intervals)
  */
}
