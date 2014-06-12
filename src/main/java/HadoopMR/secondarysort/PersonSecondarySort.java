package HadoopMR.secondarysort;

/**
 * Created by shiyao on 4/12/14.
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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

public class PersonSecondarySort {

    public static class SecondaryMapper extends Mapper<Text, Text, PersonWritableComparable, Text>{
        public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            PersonWritableComparable outKey = new PersonWritableComparable(key.toString(), value.toString());
            context.write(outKey, value);
        }
    }

    public static class SecondaryReducer extends Reducer<PersonWritableComparable, Text, Text, Text> {
        public enum Counter {
            keyCounter;
        }
        public void reduce(PersonWritableComparable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String fistName = key.getFirstName();
            for (Text val : values) {
                context.write(new Text(fistName), new Text("!"+key.toString()+"!"+val));
            }
            context.getCounter(Counter.keyCounter).increment(1);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        System.out.println("--------------\n"+conf+"--------------");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: AirRouteCount <in> <out>");
            System.exit(2);
        }
        conf.set("key.value.separator.in.input.line"," ");
        Job job = new Job(conf, "edge count");
        job.setJarByClass(PersonSecondarySort.class);
        job.setMapperClass(SecondaryMapper.class);
        job.setReducerClass(SecondaryReducer.class);
        job.setMapOutputKeyClass(PersonWritableComparable.class);
        job.setMapOutputValueClass(Text.class);
        conf.setClass("mapred.map.output.compression.codec", GzipCodec.class, CompressionCodec.class);
        conf.setBoolean("mapred.compress.map.output", true);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setPartitionerClass(FirstNamePartitioner.class);
        job.setGroupingComparatorClass(FirstNameComparator.class);
        /*
        suppose mapper output:
        shiyao 1 ...
        shiyao 3 ...
        shiyao 2 ...
        if there is by default group comparator(i.e group has same key: <shiyao 1>), there will be 3 key groups,
        however, if we set FirstNameComparator group, there will be only one key group,
         */
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
