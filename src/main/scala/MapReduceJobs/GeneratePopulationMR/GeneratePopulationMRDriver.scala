package MapReduceJobs.GeneratePopulationMR

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.filecache.DistributedCache
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{LongWritable => LW, Text => T}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}

/**
 * Created by preethu19th on 11/5/14.
 */
object GeneratePopulationMRDriver {
  def run(conf: Configuration, ip: String, op: Path, fileName: String) = {
    DistributedCache.addCacheFile(new java.net.URI(fileName), conf)
    val job = new Job(conf)
    job.setMapOutputKeyClass(classOf[T])
    job.setMapOutputValueClass(classOf[LW])
    job.setMapperClass(classOf[GeneratePopulationMapper])
    job.setNumReduceTasks(0)
    job.setInputFormatClass(classOf[TextInputFormat])
    job.setOutputFormatClass(classOf[TextOutputFormat[T, LW]])
    job.setJarByClass(GeneratePopulationMRDriver.getClass)
    FileInputFormat.setInputPaths(job, ip)
    FileOutputFormat.setOutputPath(job, op)
    job.waitForCompletion(true)
  }

}
