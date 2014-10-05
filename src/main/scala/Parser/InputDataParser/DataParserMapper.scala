package Parser.InputDataParser

import Parser.MapReduceFunctions
import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mapreduce.Mapper

/**
 * Created by preethu19th on 10/2/14.
 */

class DataParserMapper extends Mapper[LW, T, T, LW] {
  var mapRedFunc: MapReduceFunctions = _

  override def setup(conT: Mapper[LW, T, T, LW]#Context) = {
    val conf = conT.getConfiguration
    mapRedFunc = new MapReduceFunctions(conf)
  }

  override def map(key: LW, value: T,
                   conT: Mapper[LW, T, T, LW]#Context) = {
    val (mapOpKey, mapOpVal) = mapRedFunc.mapRawLine(value)
    conT.write(new T(mapOpKey), new LW(mapOpVal))
  }
}