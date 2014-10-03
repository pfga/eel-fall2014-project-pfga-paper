package Parser

import org.apache.hadoop.filecache.DistributedCache
import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mapreduce.{Reducer, Mapper}

/**
 * Created by preethu19th on 10/2/14.
 */

class DataParserMapper extends Mapper[LW, T, T, LW] {
  var mapRedFunc: MapReduceFunctions = _

  override def setup(conT: Mapper[LW, T, T, LW]#Context) = {
    val conf = conT.getConfiguration
    val cacheFiles = DistributedCache.getLocalCacheFiles(conf)
    val configFile = cacheFiles(0).toString
    mapRedFunc = new MapReduceFunctions(configFile)
  }

  override def map(key: LW, value: T,
                   conT: Mapper[LW, T, T, LW]#Context) = {
    val (mapOpKey, mapOpVal) = mapRedFunc.mapLine(value)
    conT.write(new T(mapOpKey), new LW(mapOpVal))
  }
}

class DataParserReducer extends Reducer[T, LW, T, LW] {
  var mapRedFunc: MapReduceFunctions = _

  override def setup(conT: Reducer[T, LW, T, LW]#Context) = {
    val conf = conT.getConfiguration
    val cacheFiles = DistributedCache.getLocalCacheFiles(conf)
    val configFile = cacheFiles(0).toString
    mapRedFunc = new MapReduceFunctions(configFile)
  }

  override def reduce(key: T, values: java.lang.Iterable[LW],
                      conT: Reducer[T, LW, T, LW]#Context) = {
    val redOp = mapRedFunc.reduceLine(values)
    conT.write(key, new LW(redOp))
  }
}

object DataParserMR {

  def driver(): Unit = {

  }
}