package HadoopMR.InputFormatExample;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * Created by shiyao on 4/12/14.
 */
public class AirRouteInputFormat extends FileInputFormat<AirRouteWritableComparable, IntWritable> {

    @Override
    public RecordReader<AirRouteWritableComparable, IntWritable> createRecordReader(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException,
            InterruptedException {
        return new CustomLineRecordReader();
    }
}
