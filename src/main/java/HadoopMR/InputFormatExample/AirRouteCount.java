package HadoopMR.InputFormatExample;

/**
 * Created by shiyao on 4/12/14.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class AirRouteCount {

    public static class AirRouteMapper extends Mapper<AirRouteWritableComparable, IntWritable, AirRouteWritableComparable, IntWritable>{
        public void map(AirRouteWritableComparable key, IntWritable value, Context context) throws IOException, InterruptedException {
            context.write(key, value);
        }
    }

    public static class AirRouteReducer extends Reducer<AirRouteWritableComparable,IntWritable,AirRouteWritableComparable,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(AirRouteWritableComparable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int peopleSum = 0;
            for (IntWritable val : values) {
                peopleSum += val.get();
            }
            result.set(peopleSum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        System.out.println("--------------\n"+conf+"--------------");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 3) {
            System.err.println("Usage: AirRouteCount <in1> <in2> <out>");
            System.exit(2);
        }
        Job job = new Job(conf, "AirRoute people count");
        job.setJarByClass(AirRouteCount.class);
        job.setMapperClass(AirRouteMapper.class);
        job.setCombinerClass(AirRouteReducer.class);
        job.setReducerClass(AirRouteReducer.class);
        job.setMapOutputKeyClass(AirRouteWritableComparable.class);
        job.setMapOutputValueClass(IntWritable.class);
        conf.setClass("mapred.map.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        conf.setBoolean("mapred.compress.map.output", true);
        job.setOutputKeyClass(AirRouteWritableComparable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setInputFormatClass(AirRouteInputFormat.class);
        job.setPartitionerClass(DepartureNodePartitioner.class);
        MultipleInputs.addInputPath(job, new Path(args[0]), AirRouteInputFormat.class, AirRouteMapper.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), AirRouteInputFormat.class, AirRouteMapper.class);
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
