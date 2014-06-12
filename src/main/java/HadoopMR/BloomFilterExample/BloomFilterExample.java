package HadoopMR.BloomFilterExample;

/**
 * Created by shiyao on 4/12/14.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.bloom.BloomFilter;
import org.apache.hadoop.util.bloom.Key;
import org.apache.hadoop.util.hash.Hash;

import java.io.IOException;

public class BloomFilterExample extends Configured implements Tool {

    public static class bloomfilterMapper extends Mapper<Text, Text, NullWritable, BloomFilter>{
        private BloomFilter localMapperFilter;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            int bitSize = context.getConfiguration().getInt("bits", 1000);
            int hashNum = context.getConfiguration().getInt("hashnum", 5);
            localMapperFilter = new BloomFilter(bitSize, hashNum, Hash.MURMUR_HASH);
        }

        @Override
        public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            localMapperFilter.add(new Key(key.toString().getBytes()));
            //we can call localMapperFilter.membershipTest(Key) to check if a key is in
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            context.write(NullWritable.get(), localMapperFilter);
            super.cleanup(context);
        }
    }

    public static class bloomfilterReducer extends Reducer<NullWritable, BloomFilter, BloomFilter, NullWritable> {
        private BloomFilter totalFilter;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            int bitSize = context.getConfiguration().getInt("bits", 1000);
            int hashNum = context.getConfiguration().getInt("hashnum", 1000);
            totalFilter = new BloomFilter(bitSize, hashNum, Hash.MURMUR_HASH);
        }

        @Override
        public void reduce(NullWritable key, Iterable<BloomFilter> values, Context context) throws IOException, InterruptedException {
            for (BloomFilter val : values) {
                totalFilter.or(val);
            }
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            super.cleanup(context);
            context.write(totalFilter, NullWritable.get());
        }
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new BloomFilterExample(), args));
    }

    @Override
    public int run(String[] args) throws Exception {
        /*
        false positive          bits required per element
        2%                      8.14
        1%                      9.58
        0.1%                    14.38
         */
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <hdfsin> <hdfsout>");
            System.exit(-1);
        }
        conf.setInt("bits", 30);
        conf.setInt("hashnum", 2);
        Job job = new Job(conf, "bloomfilter");
        job.setJarByClass(BloomFilterExample.class);
        job.setMapperClass(bloomfilterMapper.class);
        job.setReducerClass(bloomfilterReducer.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputKeyClass(BloomFilter.class);
        job.setOutputValueClass(NullWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        return (job.waitForCompletion(true) ? 0 : 1);
    }
}
