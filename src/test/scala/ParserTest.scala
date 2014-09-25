import HelperUtils.HelperFunctions
import Parser.ConfigKeyNames._
import Parser.{MapReduceParser, SchemaValidator, ConfigReader}
import org.scalatest.FlatSpec

/**
 * Created by preethu19th on 9/25/14.
 */
class ParserTest  extends FlatSpec {
  "Simulate MapReduce" should "give reduce output" in  {
    val mapIpLines = io.Source.fromURL(getClass.getResource("/input.txt")).getLines()

    val mapOps = mapIpLines.foldLeft(Map[String, List[Int]]()) { (mapOp, line) =>
      val (key,value) = MapReduceParser.mapLine(line)
      val opList = if (mapOp.contains(key)) {
        value :: mapOp(key)
      } else List(value)
      mapOp + Tuple2(key, opList)
    }

    mapOps.foreach { mapOpLine =>
      val (key, values) = mapOpLine
      val redOp = MapReduceParser.reduceLine(values)
      println(key + "_" + redOp)
    }
  }
}