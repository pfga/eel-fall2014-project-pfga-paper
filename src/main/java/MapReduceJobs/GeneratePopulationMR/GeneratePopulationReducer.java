package MapReduceJobs.GeneratePopulationMR;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class GeneratePopulationReducer extends Reducer<NullWritable, Text, NullWritable, Text> {
}
