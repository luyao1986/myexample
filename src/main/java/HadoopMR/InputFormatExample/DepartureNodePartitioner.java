package HadoopMR.InputFormatExample;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by shiyao on 4/12/14.
 */
public class DepartureNodePartitioner extends Partitioner<AirRouteWritableComparable, Writable> {
    @Override
    public int getPartition(AirRouteWritableComparable key, Writable value, int numPartitions)
    {
        return key.getDepartureNode().hashCode() % numPartitions;
    }
}
