package Parser

import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mrunit.mapreduce.{MapDriver, ReduceDriver}
import org.scalatest.FlatSpec

/**
 * Created by preethu19th on 10/2/14.
 */
class DataParserMRTest extends FlatSpec {

  val mapper = new DataParserMapper
  val mapDriver = MapDriver.newMapDriver(mapper)
  mapDriver.addCacheFile("parse-config.properties")

  val reducer = new DataParserReducer
  val redDriver = ReduceDriver.newReduceDriver(reducer)
  redDriver.addCacheFile("parse-config.properties")

  "Map" should "produce appropriate output" in {
    mapDriver.withInput(new LW(), new T("2010-01-01,10,something,something"))
    mapDriver.withInput(new LW(), new T("2010-01-02,20"))
    mapDriver.withOutput(new T("2010-01-01"), new LW(10))
    mapDriver.withOutput(new T("2010-01-02"), new LW(20))
    mapDriver.runTest()
  }

  "Reduce" should "produce appropriate output" in {
    val values: java.util.List[LW] = java.util.Arrays.asList(new LW(10))
    redDriver.withInput(new T("2010-01-01"), values)
    redDriver.withOutput(new T("2010-01-01"), new LW(10))
    redDriver.runTest()
  }
}