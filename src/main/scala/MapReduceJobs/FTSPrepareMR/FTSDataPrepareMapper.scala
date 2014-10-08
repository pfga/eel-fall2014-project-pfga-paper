package Parser.FTSPrepareMR

import MapReduceJobs.MapReduceFunctions
import Parser.CommonMR
import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mapreduce.Mapper

/**
 * Created by preethu19th on 10/2/14.
 */

class FTSDataPrepareMapper extends Mapper[LW, T, T, LW] with CommonMR {
  var mapRedFunc: MapReduceFunctions = _

  override def setup(conT: Mapper[LW, T, T, LW]#Context) = {
    mapRedFunc = new MapReduceFunctions(conT.getConfiguration)
  }

  override def map(key: LW, value: T,
                   conT: Mapper[LW, T, T, LW]#Context) = {
    val (mapOpKey, mapOpVal) = mapRedFunc.mapFTSIp(value)
    checkMinMax(mapOpVal)
    conT.write(new T(mapOpKey), new LW(mapOpVal))
  }

  override def cleanup(conT: Mapper[LW, T, T, LW]#Context) = {
    conT.write(new T("min"), new LW(min))
    conT.write(new T("max"), new LW(max))
  }
}