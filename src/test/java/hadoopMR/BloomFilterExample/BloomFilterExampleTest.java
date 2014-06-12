package hadoopMR.BloomFilterExample;

import HadoopMR.BloomFilterExample.BloomFilterExample;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.apache.hadoop.util.bloom.BloomFilter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by shiyao on 5/11/14.
 */
public class BloomFilterExampleTest {
    MapDriver<Text, Text, NullWritable, BloomFilter> mapDriver;
    ReduceDriver<NullWritable, BloomFilter, BloomFilter, NullWritable> reduceDriver;
    MapReduceDriver<Text, Text, NullWritable, BloomFilter, BloomFilter, NullWritable> mapReduceDriver;

    @Before
    public void setUp() {
        BloomFilterExample.bloomfilterMapper mapper = new BloomFilterExample.bloomfilterMapper();
        BloomFilterExample.bloomfilterReducer reducer = new BloomFilterExample.bloomfilterReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testMR() throws IOException, URISyntaxException {
        mapReduceDriver.getConfiguration().setInt("bits", 30);
        mapReduceDriver.getConfiguration().setInt("hashnum", 2);
        mapReduceDriver.withInput(new Text("shiyao"), new Text(""));
        mapReduceDriver.withInput(new Text("shiyao"), new Text(""));
        mapReduceDriver.withInput(new Text("monkey"), new Text(""));
        mapReduceDriver.withInput(new Text("yulu"), new Text(""));
        List<Pair<BloomFilter, NullWritable>> result = mapReduceDriver.run();
        assertEquals(result.get(0).toString(), "({5, 9, 10, 13, 14}, (null))");
    }
}
