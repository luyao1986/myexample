package HadoopMR.shortestPath;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ShortestPath extends Configured implements Tool {
    private static final int MAXDISTANCE=1000;

    public enum color {
        grey("grey"),
        black("black");

        String col;
        color(String c) {
            this.col = c;
        }
    }

    public static class NodeValue {
        color c = color.black;
        int distance;
        String preNode;
        Map<String,Integer> ajacyList = new HashMap<>();
        NodeValue(Text value) {
            String[] vals = value.toString().split("\\|");
            if(vals[0].equals("grey")) {c = color.grey;}
            distance = Integer.parseInt(vals[1]);
            preNode = vals[2];
            if(vals.length>3) {
                for(String kv: vals[3].split(";")) {
                    String[] nodeDistance = kv.split(",");
                    ajacyList.put(nodeDistance[0], Integer.parseInt(nodeDistance[1]));
                }
            }
        }

        NodeValue() {
            distance = MAXDISTANCE;
        }

        public Text getValue() {
            StringBuffer sb = new StringBuffer();
            sb.append(c).append("|").append(distance).append("|").append(preNode).append("|");
            boolean first = true;
            for(String node: ajacyList.keySet()) {
                if(first) {
                    sb.append(node+","+ajacyList.get(node));
                    first = false;
                }
                else {
                    sb.append(";"+node+","+ajacyList.get(node));
                }
            }
            return new Text(sb.toString());
        }
    }

    public static class ShortestPathMapper extends Mapper<Text, Text, Text, Text>{
        @Override
        public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
            //key=node1 value="white/grey/black|distance|preNode|nodei,distancei;nodej,distancej"
            NodeValue nv = new NodeValue(value);
            if(nv.c.col.equals("grey")) {
                for(String node: nv.ajacyList.keySet()) {
                    int distance = nv.ajacyList.get(node)+nv.distance;
                    context.write(new Text(node), new Text("grey|"+distance+"|"+key));
                }
                nv.c = color.black;
                context.write(key, nv.getValue());
            }
            else {
                context.write(key, value);
            }
        }
    }

    public static class ShortestPathReducer extends Reducer<Text,Text,Text,Text> {
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            NodeValue min = new NodeValue();
            NodeValue max = null;
            for(Text value: values) {
                NodeValue nv = new NodeValue(value);
                if(nv.c.col.equals("black"))
                    max = nv;
                else if(nv.distance < min.distance) {
                    min = nv;
                }
            }
            if(min.distance == MAXDISTANCE)
            {
                min = max;
            }
            min.ajacyList = max.ajacyList;
            context.getCounter(min.c).increment(1);
            context.write(key, min.getValue());
        }
    }

    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new ShortestPath(), args));
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 1) {
            System.err.println("Usage: wordcount <hdfsin>");
            System.exit(-1);
        }
        boolean first = true;
        int i=0;
        while(true) {
            i++;
            Path input;
            Path output;
            if(first) {
                input = new Path(otherArgs[0]);
                output = new Path(otherArgs[0]+i);
            }
            else {
                input = new Path(otherArgs[0]+(i-1));
                output = new Path(otherArgs[0]+i);
            }
            Job job = new Job(conf, "shortest path");
            job.setJarByClass(ShortestPath.class);
            job.setMapperClass(ShortestPathMapper.class);
            job.setReducerClass(ShortestPathReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setInputFormatClass(KeyValueTextInputFormat.class);
            FileInputFormat.addInputPath(job, input);
            FileOutputFormat.setOutputPath(job, output);
            int ret = job.waitForCompletion(true) ? 0 : 1;
            if(job.getCounters().findCounter(color.grey).getValue() == 0) {
                System.out.println("done");
                break;
            }
        }
        return 0;
    }
}
