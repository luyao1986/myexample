package oozie;

/**
 * Created by shiyao on 4/14/14.
 */

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartOozieJavaClient {
    OozieClient wc; //http://127.0.0.1:11000/oozie
    Properties conf;

    public static void main(String... args) throws IOException, OozieClientException, InterruptedException {
        if (args.length != 4) {
            System.err.println("Expected parameters: <type=1=workflow,2=coordinate,other=bundle> <oozie WS URL> <hdfs work path> <localPropertyFile>");
            System.exit(-1);
        }
        StartOozieJavaClient oozieClient = new StartOozieJavaClient(args[0], args[1], args[2], args[3]);
        oozieClient.start();
    }

    public StartOozieJavaClient(String type, String webURL, String hdfsWorkPath, String propertyFile) throws IOException {
        wc = new OozieClient(webURL);
        conf = wc.createConfiguration();
        if(type.equals("1"))
            conf.setProperty(OozieClient.APP_PATH, hdfsWorkPath);
        else if(type.equals("2")) {
            conf.setProperty(OozieClient.COORDINATOR_APP_PATH, hdfsWorkPath);
        }
        else {
            conf.setProperty(OozieClient.BUNDLE_APP_PATH, hdfsWorkPath);
        }
        //we must make sure the workflow.xml exist on grid
        conf.load(new FileInputStream(propertyFile));
    }

    public void start() throws OozieClientException, InterruptedException {
        String jobId = wc.run(conf);
        System.out.println("Workflow jobid:"+jobId+" submitted");
    }
}
