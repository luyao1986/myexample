package hadoopMR.MatrixMuliplication;

import HadoopMR.MatrixMuliplication.MatrixMultiplication;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by shiyao on 5/6/14.
 */
//TODO try use PipelineMapReduceDriver to test multiple jobs
//TODO try test multipleInputs, multipleOutputs
public class MatrixMultiplicationTest {
    MapDriver<Object, Text, Text, Text> mapDriver;
    ReduceDriver<Text, Text, Text, IntWritable> reduceDriver;
    MapReduceDriver<Object, Text, Text, Text, Text, IntWritable> mapReduceDriver;

    @Before
    public void setUp() {
        MatrixMultiplication.MatrixMapper mapper = new MatrixMultiplication.MatrixMapper();
        MatrixMultiplication.MultiplicationReducer reducer = new MatrixMultiplication.MultiplicationReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testMR() throws IOException, URISyntaxException {
        mapReduceDriver.withInput(new LongWritable(), new Text("A,0,0,5"));
        mapReduceDriver.withInput(new LongWritable(), new Text("B,0,0,2"));
        List<Pair<Text, IntWritable>> result = mapReduceDriver.run();
        System.out.println(result.get(0));
        assertEquals(result.get(0).toString(), "(0,0, 10)");
    }

    @Test
    public void testReducer() throws IOException {
        List<Text> list = new ArrayList<Text>();
        list.add(new Text("A,0,1"));
        list.add(new Text("A,1,1"));
        list.add(new Text("B,0,1"));
        list.add(new Text("B,1,1"));
        reduceDriver.withInput(new Text("1,1"), list);
        reduceDriver.withOutput(new Text("1,1"), new IntWritable(2));
        reduceDriver.runTest();
    }

    @Test
         public void testMapper() throws IOException {
        //First style is to tell the framework both input and output values and let the framework do the assertions
        mapDriver.withInput(new LongWritable(), new Text("A,0,0,5"));
        mapDriver.addOutput(new Text("0,0"), new Text("A,0,5"));
        mapDriver.addOutput(new Text("0,1"), new Text("A,0,5"));
        mapDriver.addOutput(new Text("0,2"), new Text("A,0,5"));
        mapDriver.addOutput(new Text("0,3"), new Text("A,0,5"));
        mapDriver.runTest();
    }

    @Test
    public void testMapper2() throws IOException {
        //use traditional approach where you do the assertion yourself
        mapDriver.withInput(new LongWritable(), new Text("A,0,0,5"));
        final List<Pair<Text, Text>> result = mapDriver.run();
        assertEquals(result.size(), 4);
    }
}
