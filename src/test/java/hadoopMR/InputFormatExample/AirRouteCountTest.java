package hadoopMR.InputFormatExample;

import HadoopMR.InputFormatExample.AirRouteCount;
import HadoopMR.InputFormatExample.AirRouteWritableComparable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

/**
 * Created by shiyao on 5/7/14.
 */
public class AirRouteCountTest {
    MapDriver<AirRouteWritableComparable, IntWritable, AirRouteWritableComparable, IntWritable> mapDriver;
    ReduceDriver<AirRouteWritableComparable,IntWritable,AirRouteWritableComparable,IntWritable> reduceDriver;
    MapReduceDriver<AirRouteWritableComparable, IntWritable, AirRouteWritableComparable, IntWritable, AirRouteWritableComparable,IntWritable> mapReduceDriver;

    @Before
    public void setUp() {
        AirRouteCount.AirRouteMapper mapper = new AirRouteCount.AirRouteMapper();
        AirRouteCount.AirRouteReducer reducer = new AirRouteCount.AirRouteReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
        mapReduceDriver.setCombiner(reducer);
    }

    @Test
    public void testMR() throws IOException, URISyntaxException {
        mapReduceDriver.withInput(new AirRouteWritableComparable("beijing", "yantai"), new IntWritable(100));
        mapReduceDriver.withInput(new AirRouteWritableComparable("beijing", "yantai"), new IntWritable(50));
        List<Pair<AirRouteWritableComparable, IntWritable>> result = mapReduceDriver.run();
        Assert.assertEquals(result.get(0).toString(), "(beijing->yantai, 150)");
    }
}
