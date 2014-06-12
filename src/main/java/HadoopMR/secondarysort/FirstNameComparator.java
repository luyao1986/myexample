package HadoopMR.secondarysort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * Created by shiyao on 4/27/14.
 */
public class FirstNameComparator extends WritableComparator {
    public FirstNameComparator() {
        super(PersonWritableComparable.class, true);
    }

    @Override
    public int compare(WritableComparable o1, WritableComparable o2) {
        PersonWritableComparable p1 = (PersonWritableComparable) o1;
        PersonWritableComparable p2 = (PersonWritableComparable) o2;
        return p1.getFirstName().compareTo(p2.getFirstName());
    }
}
