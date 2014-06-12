package HadoopMR.InputFormatExample;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by shiyao on 4/12/14.
 */
public class AirRouteWritableComparable implements WritableComparable<AirRouteWritableComparable> {
    private String departureNode;
    private String arrivalNode;

    public AirRouteWritableComparable() {} //we must add this if we add below func

    public AirRouteWritableComparable(String departureNode, String arrivalNode) {
        this.departureNode = departureNode;
        this.arrivalNode = arrivalNode;
    }

    public String getArrivalNode() {
        return arrivalNode;
    }

    public String getDepartureNode() { return departureNode;}

    @Override
    public void readFields(DataInput in) throws IOException {
        departureNode = in.readUTF();
        arrivalNode = in.readUTF();
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(departureNode);
        out.writeUTF(arrivalNode);
    }
    @Override
    public int compareTo(AirRouteWritableComparable o) {
        return (departureNode.compareTo(o.departureNode) != 0)
                ? departureNode.compareTo(o.departureNode)
                : arrivalNode.compareTo(o.arrivalNode);
    }

    @Override
    public String toString() {
        return getDepartureNode()+"->"+getArrivalNode();
    }
}
