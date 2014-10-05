package Parser.FTSPrepareMR

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{Text => T}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}

/**
 * Created by preethu19th on 10/2/14.
 */
object FTSDataPrepareMRDriver {
  def run(conf: Configuration,ip: String, op: Path, propFile: String) = {
    val job = new Job(conf)
    job.setMapOutputKeyClass(classOf[T])
    job.setMapOutputValueClass(classOf[T])
    job.setMapperClass(classOf[FTSDataPrepareMapper])
    job.setReducerClass(classOf[FTSDataPrepareReducer])
    job.setNumReduceTasks(1)
    job.setInputFormatClass(classOf[TextInputFormat])
    job.setOutputFormatClass(classOf[TextOutputFormat[T, T]])
    job.setJarByClass(FTSDataPrepareMRDriver.getClass)
    FileInputFormat.setInputPaths(job, ip)
    FileOutputFormat.setOutputPath(job, op)
    job.waitForCompletion(true)
  }
}