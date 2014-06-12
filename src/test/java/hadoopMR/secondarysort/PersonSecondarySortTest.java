package hadoopMR.secondarysort;

import HadoopMR.WordCount.WordCount;
import HadoopMR.secondarysort.FirstNameComparator;
import HadoopMR.secondarysort.PersonSecondarySort;
import HadoopMR.secondarysort.PersonWritableComparable;
import org.apache.hadoop.io.RawComparator;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by shiyao on 5/6/14.
 */
public class PersonSecondarySortTest {
    MapDriver<Text, Text, PersonWritableComparable, Text> mapDriver;
    ReduceDriver<PersonWritableComparable, Text, Text, Text> reduceDriver;
    MapReduceDriver<Text, Text, PersonWritableComparable, Text, Text, Text> mapReduceDriver;

    @Before
    public void setUp() {
        PersonSecondarySort.SecondaryMapper mapper = new PersonSecondarySort.SecondaryMapper();
        PersonSecondarySort.SecondaryReducer reducer = new PersonSecondarySort.SecondaryReducer();
        mapDriver = MapDriver.newMapDriver(mapper);
        reduceDriver = ReduceDriver.newReduceDriver(reducer);
        mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
    }

    @Test
    public void testSecondarySort() throws IOException, URISyntaxException {
        mapReduceDriver.withInput(new Text("shiyao"), new Text("qian"));
        mapReduceDriver.withInput(new Text("shiyao"), new Text("melody"));
        mapReduceDriver.withKeyGroupingComparator(new FirstNameComparator());
        mapReduceDriver.run();
        assertEquals("Expected Counter.KeyCounter one", 1, mapReduceDriver.getCounters().findCounter(PersonSecondarySort.SecondaryReducer.Counter.keyCounter).getValue());
    }

    @Test
    public void testNonSecondarySort() throws IOException, URISyntaxException {
        mapReduceDriver.withInput(new Text("shiyao"), new Text("qian"));
        mapReduceDriver.withInput(new Text("shiyao"), new Text("melody"));
        List<Pair<Text, Text>> result = mapReduceDriver.run();
        for(Pair<Text, Text> tmp: result)
            System.out.println(tmp);
        assertEquals("Expected Counter.KeyCounter one", 2, mapReduceDriver.getCounters().findCounter(PersonSecondarySort.SecondaryReducer.Counter.keyCounter).getValue());
    }
}
