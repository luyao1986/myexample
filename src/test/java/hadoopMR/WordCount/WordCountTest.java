package hadoopMR.WordCount;

import HadoopMR.WordCount.WordCount;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * MRUnit can not work with testng, only works for Junit
 */
public class WordCountTest {
    MapDriver<Object, Text, Text, IntWritable> mapDriver;
    ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
    MapReduceDriver<Object, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

    @Before
    public void setUp() {
        WordCount.TokenizerMapper mapper = new WordCount.TokenizerMapper();
        WordCount.IntSumReducer reducer = new WordCount.IntSumReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        mapReduceDriver.setCombiner(reducer);
        Configuration configuration = mapDriver.getConfiguration();
        configuration.set("is.case.ignored", "true");
    }

    @Test
    public void testMapper() throws IOException {
        mapDriver.withInput(new LongWritable(), new Text("This is a simple statement it is dummy"));
        mapDriver.addOutput(new Text("this"), new IntWritable(1));
        mapDriver.addOutput(new Text("is"), new IntWritable(1));
        mapDriver.addOutput(new Text("a"), new IntWritable(1));
        mapDriver.addOutput(new Text("simple"), new IntWritable(1));
        mapDriver.addOutput(new Text("statement"), new IntWritable(1));
        mapDriver.addOutput(new Text("it"), new IntWritable(1));
        mapDriver.addOutput(new Text("is"), new IntWritable(1));
        mapDriver.addOutput(new Text("dummy"), new IntWritable(1));

        mapDriver.runTest();
        assertEquals("Expected Counter.INPUT_RECORD_NUM is one", 1, mapDriver.getCounters().findCounter(WordCount.TokenizerMapper.Counter.INPUT_RECORD_NUM).getValue());
    }

    @Test
    public void testReducer() throws IOException {
        List<IntWritable> list = new ArrayList<IntWritable>();
        list.add(new IntWritable(1));
        list.add(new IntWritable(5));
        reduceDriver.withInput(new Text("this"), list);
        reduceDriver.withOutput(new Text("this"), new IntWritable(6));
        reduceDriver.runTest();
        assertEquals("Expected Counter.UNIQUE_WORD_NUM is one", 1, reduceDriver.getCounters().findCounter(WordCount.IntSumReducer.Counter.UNIQUE_WORD_NUM).getValue());
    }

    @Test
    public void testMR() throws IOException {
        mapReduceDriver.setKeyOrderComparator(new Text.Comparator());
//        mapReduceDriver.getConfiguration().set("mapreduce.job.reduces", "1");
        mapReduceDriver.getConfiguration().set("is.case.ignored", "true");
        mapReduceDriver.withInput(new LongWritable(), new Text("This this this this this"));
        mapReduceDriver.addOutput(new Text("this"), new IntWritable(5));
        mapReduceDriver.runTest();
    }
}
