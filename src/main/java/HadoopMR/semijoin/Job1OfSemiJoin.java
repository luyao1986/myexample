package HadoopMR.semijoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * the output of Job1OfSemiJoin$Reduce can be cached by later semi-joins
 * Created by shiyao on 4/27/14.
 */
public class Job1OfSemiJoin {
    public static class Map extends Mapper<Text, Text, Text, NullWritable> {
        private Set<String> keys = new HashSet<String>(); //Create the HashSet to cache the user names.

        @Override
        protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            keys.add(key.toString());
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            Text outputKey = new Text();
            for(String key: keys) {
                outputKey.set(key);
                context.write(outputKey, NullWritable.get());
            }
        }
    }

    public static class Reduce extends Reducer<Text, NullWritable, Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("key.value.separator.in.input.line"," ");
        System.out.println("--------------\n"+conf+"--------------");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: AirRouteCount <hdfsin> <hdfsout>");
            System.exit(2);
        }
        Job job = new Job(conf, "job 1 of semi join");
        job.setJarByClass(Job1OfSemiJoin.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setNumReduceTasks(1);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);
        conf.setClass("mapred.map.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        conf.setBoolean("mapred.compress.map.output", true);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
