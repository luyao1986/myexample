package HadoopMR.secondarysort;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by shiyao on 4/12/14.
 */
public class FirstNamePartitioner extends Partitioner<PersonWritableComparable, Writable> {
    @Override
    public int getPartition(PersonWritableComparable key, Writable value, int numPartitions)
    {
        return key.getFirstName().hashCode() % numPartitions;
    }
}
