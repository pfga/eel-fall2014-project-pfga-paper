package Parser

import MapReduceJobs.MapReduceFunctions
import org.apache.hadoop.conf.Configuration
import org.scalatest.FlatSpec

/**
 * Created by preethu19th on 10/5/14.
 */
class MapReduceFunctionsTest extends FlatSpec {
  val a = new MapReduceFunctions(new Configuration())
  "dtFormatConvert" should "convertdt from ip to op format" in {
    assert(a.dtFormatConvert("2010-01-01") == "2010")
  }

}
