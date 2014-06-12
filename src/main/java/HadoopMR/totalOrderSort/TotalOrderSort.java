package HadoopMR.totalOrderSort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TotalOrderSort extends Configured implements Tool {


    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new TotalOrderSort(), args));
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 4) {
            System.err.println("Usage: wordcount <in> <out> <partition file> <reducernumer>");
            System.exit(1);
        }
        Path inputPath = new Path(args[0]);
        Path outputPath = new Path(args[1]);
        Path partitionFile = new Path(args[2]);
        int numReducers = Integer.parseInt(args[3]);
        InputSampler.Sampler<Text, Text> sampler = new InputSampler.RandomSampler<Text,Text> (0.1, 10000, 10);
        //there are also InputSampler.SplitSampler and RandomSampler
        TotalOrderPartitioner.setPartitionFile(conf, partitionFile);

        Job job = new Job(conf, "total order sort");
        job.setJarByClass(TotalOrderSort.class);
        job.setNumReduceTasks(numReducers);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setPartitionerClass(TotalOrderPartitioner.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);
        outputPath.getFileSystem(conf).delete(outputPath, true);
        InputSampler.writePartitionFile(job, sampler);
        return job.waitForCompletion(true)? 0 : 1;
    }
}
