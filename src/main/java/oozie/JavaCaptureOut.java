package oozie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by shiyao on 4/16/14.
 */
public class JavaCaptureOut {
    //oozie.action.output.properties ---> capture-output
    //oozie.action.id ---> actionID, oozie.job.id--->workflowID
    //oozie.action.conf.xml storage all configuration of this java action, so we can use it to trigger later MR,
    //PigServer...... by conf.addResource(new FileInputStream(System.getProperty("oozie.action.conf.xml"))); please note that all the
    //oozie.launcher.*** in java action property will be read by conf automatically.
    public static void main(String[] args) throws IOException {
        File file = new File(System.getProperty("oozie.action.output.properties"));
        Properties props = new Properties();
        props.setProperty("path", args[0]);
        OutputStream os = new FileOutputStream(file);
        props.store(os, "");
        os.close();
        System.out.println(file.getAbsolutePath());
    }
}
