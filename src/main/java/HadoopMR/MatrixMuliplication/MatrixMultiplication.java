package HadoopMR.MatrixMuliplication;

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
import java.util.HashMap;

//pseudocode
//        map(key, value):
//        // value is ("A", i, j, a_ij) or ("B", j, k, b_jk)
//        if value[0] == "A":
//        i = value[1]
//        j = value[2]
//        a_ij = value[3]
//        for k = 1 to p:
//        emit((i, k), (A, j, a_ij))
//        else:
//        j = value[1]
//        k = value[2]
//        b_jk = value[3]
//        for i = 1 to m:
//        emit((i, k), (B, j, b_jk))
//
//        reduce(key, values):
//        // key is (i, k)
//        // values is a list of ("A", j, a_ij) and ("B", j, b_jk)
//        hash_A = {j: a_ij for (x, j, a_ij) in values if x == A}
//        hash_B = {j: b_jk for (x, j, b_jk) in values if x == B}
//        result = 0
//        for j = 1 to n:
//        result += hash_A[j] * hash_B[j]
//        emit(key, result)
public class MatrixMultiplication extends Configured implements Tool {

    private final static int M = 2;    //Matrix A is 2*5
    private final static int N = 5;
    private final static int P = 4;    //Matrix B is 5*4
    private final static String delemeter = ",";

    public static class MatrixMapper extends Mapper<Object, Text, Text, Text>{
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] tmp = value.toString().split(delemeter);
            assert(tmp.length == 4); //A|B i|j j|k aij|bjk
            if(tmp[0].equals("A")) {
                for(int i=0;i<P;i++) {
                    context.write(new Text(tmp[1]+delemeter+i), new Text(tmp[0]+delemeter+tmp[2]+delemeter+tmp[3]));
                }
            }
            else {
                for(int i=0;i<M;i++) {
                    context.write(new Text(i+delemeter+tmp[2]), new Text(tmp[0]+delemeter+tmp[1]+delemeter+tmp[3]));
                }
            }
        }
    }

    public static class MultiplicationReducer extends Reducer<Text, Text, Text, IntWritable> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            HashMap<Integer, Integer> hashA = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> hashB = new HashMap<Integer, Integer>();
            for (Text val : values) {
                String[] tmp = val.toString().split(delemeter);
                if(tmp[0].equals("A")) {
                    hashA.put(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
                }
                else {
                    hashB.put(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
                }
            }
            int sum = 0;
            for(int i=0;i<N;i++) {
                int aij = hashA.containsKey(i)?hashA.get(i):0;
                int bij = hashB.containsKey(i)?hashB.get(i):0;
                sum += aij*bij;
            }
            if(sum != 0)
                context.write(key, new IntWritable(sum));
        }
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new MatrixMultiplication(), args));
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <hdfsin> <hdfsout>");
            System.exit(-1);
        }
        Job job = new Job(conf, "matrix multiplication");
        job.setJarByClass(MatrixMultiplication.class);
        job.setMapperClass(MatrixMapper.class);
        job.setReducerClass(MultiplicationReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        return (job.waitForCompletion(true) ? 0 : 1);
    }
}
