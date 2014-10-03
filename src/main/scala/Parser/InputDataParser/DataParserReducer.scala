package Parser.InputDataParser

import Parser.{CommonMR, MapReduceFunctions}
import org.apache.hadoop.filecache.DistributedCache
import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mapreduce.Reducer

class DataParserReducer extends Reducer[T, LW, T, LW] with CommonMR {
  var mapRedFunc: MapReduceFunctions = _

  override def setup(conT: Reducer[T, LW, T, LW]#Context) = {
    val conf = conT.getConfiguration
    val cacheFiles = DistributedCache.getLocalCacheFiles(conf)
    val configFile = cacheFiles(0).toString
    mapRedFunc = new MapReduceFunctions(configFile)
  }

  override def reduce(key: T, values: java.lang.Iterable[LW],
                      conT: Reducer[T, LW, T, LW]#Context) = {
    val redOp = mapRedFunc.reduceRawLine(values)
    conT.write(key, new LW(redOp))
    checkMinMax(redOp)
  }

  override def cleanup(conT: Reducer[T, LW, T, LW]#Context) = {
    conT.write(new T("min"), new LW(min))
    conT.write(new T("max"), new LW(max))
  }
}