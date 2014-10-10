package Parser.FTSPrepareMR

import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mrunit.mapreduce.{MapDriver, ReduceDriver}
import org.scalatest.FlatSpec

/**
 * Created by preethu19th on 10/5/14.
 */
class FTSDataPrepareMRTest extends FlatSpec {
  val mapper = new FTSDataPrepareMapper
  val mapDriver = MapDriver.newMapDriver(mapper)

  val reducer = new FTSDataPrepareReducer
  val redDriver = ReduceDriver.newReduceDriver(reducer)

  "FTSDataPrepareMapper" should "produce appropriate output" in {
    mapDriver.withInput(new LW(), new T("2010\t1010"))
    mapDriver.withInput(new LW(), new T("2011\t20"))
    mapDriver.withInput(new LW(), new T("2012\t20000"))
    mapDriver.withOutput(new T("2010"), new LW(1010))
    mapDriver.withOutput(new T("2011"), new LW(20))
    mapDriver.withOutput(new T("2012"), new LW(20000))
    mapDriver.withOutput(new T("min"), new LW(20))
    mapDriver.withOutput(new T("max"), new LW(20000))
    mapDriver.runTest()
  }


  "FTSDataPrepareReducer" should "produce appropriate output" in {
    val values1: java.util.List[LW] = java.util.Arrays.asList(new LW(1010))
    val valuesMinMax: java.util.List[LW] = java.util.Arrays.asList(new LW(10), new LW(20), new LW(30))

    redDriver.withInput(new T("2010"), values1)
    redDriver.withInput(new T("2011"), values1)

    redDriver.withInput(new T("min"), valuesMinMax)
    redDriver.withInput(new T("max"), valuesMinMax)
    redDriver.withOutput(new T("2010"), new LW(1010))
    redDriver.withOutput(new T("2011"), new LW(1010))
    redDriver.withOutput(new T("min"), new LW(10))
    redDriver.withOutput(new T("max"), new LW(30))
    redDriver.runTest()
  }
}