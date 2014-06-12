package HadoopMR.secondarysort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by shiyao on 4/12/14.
 */
public class PersonWritableComparable implements WritableComparable<PersonWritableComparable> {
    private String firstName;
    private String secondName;

    public PersonWritableComparable() {} //we must add this if we add below func

    public PersonWritableComparable(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getSecondName() { return secondName; }

    public String getFirstName() { return firstName;}

    @Override
    public void readFields(DataInput in) throws IOException {
        firstName = in.readUTF();
        secondName = in.readUTF();
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(firstName);
        out.writeUTF(secondName);
    }
    @Override
    public int compareTo(PersonWritableComparable o) {
        return (firstName.compareTo(o.firstName) != 0)
                ? firstName.compareTo(o.firstName)
                : secondName.compareTo(o.secondName);
    }

    @Override
    public String toString() {
        return getFirstName()+"->"+ getSecondName();
    }
}
