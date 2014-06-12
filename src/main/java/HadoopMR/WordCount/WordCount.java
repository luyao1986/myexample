package HadoopMR.WordCount;

/**
 * Created by shiyao on 4/12/14.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.StringTokenizer;

public class WordCount extends Configured implements Tool {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{

        private final static IntWritable one = new IntWritable(1);
        private Boolean isCaseIgnored = false;

        public enum Counter {
            INPUT_RECORD_NUM;
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            isCaseIgnored = context.getConfiguration().getBoolean("is.case.ignored", Boolean.FALSE);
        }

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            // add the null condigtion for mockio test, for MR unit, we don't need this.
            if (null != context.getCounter(Counter.INPUT_RECORD_NUM)) {
                context.getCounter(Counter.INPUT_RECORD_NUM).increment(1);
            }

            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String word = itr.nextToken();
                if (isCaseIgnored) {
                    word = word.toLowerCase();
                }
                context.write(new Text(word), one);
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }

    public static class IntSumReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public enum Counter {
            UNIQUE_WORD_NUM;
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
        }

        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
            context.getCounter(Counter.UNIQUE_WORD_NUM).increment(1);
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
        }
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new WordCount(), args));
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <hdfsin> <hdfsout>");
            System.exit(-1);
        }
        Job job = new Job(conf, "word count");
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        return (job.waitForCompletion(true) ? 0 : 1);
    }
}
