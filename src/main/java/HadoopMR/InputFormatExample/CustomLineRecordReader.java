package HadoopMR.InputFormatExample;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

import java.io.IOException;

/**
 * Created by shiyao on 4/12/14.
 */
public class CustomLineRecordReader extends RecordReader<AirRouteWritableComparable, IntWritable>{
    private LineRecordReader lineRecordReader;
    private AirRouteWritableComparable key;
    private IntWritable value;

    public CustomLineRecordReader() throws IOException {
        lineRecordReader = new LineRecordReader();
    }

    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        lineRecordReader.initialize(inputSplit, taskAttemptContext);
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if(lineRecordReader.nextKeyValue() == false)
        {
            return false;
        }
        String lineValue = lineRecordReader.getCurrentValue().toString();
        String[] line = lineValue.split(" ");
        if(line.length != 3)
        {
            key = new AirRouteWritableComparable("?", "?");
            value = new IntWritable(0);
        }
        else {
            key = new AirRouteWritableComparable(line[0], line[1]);
            value = new IntWritable(Integer.parseInt(line[2]));
        }

        return true;
    }

    @Override
    public AirRouteWritableComparable getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public IntWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        return lineRecordReader.getProgress();
    }

    @Override
    public void close() throws IOException {
        lineRecordReader.close();
    }

}
