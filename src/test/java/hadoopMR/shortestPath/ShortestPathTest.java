package hadoopMR.shortestPath;

import HadoopMR.shortestPath.ShortestPath;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


/**
 * Created by shiyao on 5/17/14.
 */
public class ShortestPathTest {
    MapDriver<Text, Text, Text, Text> mapDriver;
    ReduceDriver<Text, Text, Text, Text> reduceDriver;
    MapReduceDriver<Text, Text, Text, Text, Text, Text> mapReduceDriver;

    @Before
    public void setUp() {
        ShortestPath.ShortestPathMapper mapper = new ShortestPath.ShortestPathMapper();
        ShortestPath.ShortestPathReducer reducer = new ShortestPath.ShortestPathReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testMR() throws IOException, URISyntaxException {
        //job 1
//        mapReduceDriver.withInput(new Text("node0"), new Text("grey|0|node0|node1,2;node2,5"));
//        mapReduceDriver.withInput(new Text("node1"), new Text("black|1000|node1|node2,1;node3,100"));
//        mapReduceDriver.withInput(new Text("node2"), new Text("black|1000|node1|node3,9"));
        //job 2
        mapReduceDriver.withInput(new Text("node0"), new Text("black|0|node0|node2,5;node1,2"));
        mapReduceDriver.withInput(new Text("node1"), new Text("grey|2|node0|node2,1;node3,100"));
        mapReduceDriver.withInput(new Text("node2"), new Text("grey|5|node0|node3,9"));
        List<Pair<Text, Text>> result = mapReduceDriver.run();
        for(Pair<Text, Text> pair: result)
            System.out.println(pair);
    }
}
