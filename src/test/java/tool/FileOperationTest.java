package tool;

import org.junit.Test;

/**
 * Created by shiyao on 4/25/14.
 */
public class FileOperationTest {
    @Test
    public void testDeleteLocalFile() throws Exception {
        FileOperation.deleteLocalFileByHadoopLocalFS("/Users/shiyao/panel_segment_737036_20130519_20130519.dat.gz");
    }

    @Test
    public void testDeleteLocalFile2() throws Exception {
        FileOperation.deleteLocalFileByLocalFS("/Users/shiyao/pig_1397487722519.log");
    }
}
