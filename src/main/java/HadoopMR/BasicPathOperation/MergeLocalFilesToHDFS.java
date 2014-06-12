package HadoopMR.BasicPathOperation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by shiyao on 4/12/14.
 */
public class MergeLocalFilesToHDFS {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        System.out.println("conf is "+conf.toString());
        //FileSystem can be local or hdfs
        FileSystem hdfs  = FileSystem.get(conf);
        FileSystem local = FileSystem.getLocal(conf);
        //Path to encode file/directory and FileStatus to store metadata
        Path localInputDir = new Path(args[0]);
        Path hdfsFile = new Path(args[1]);
        try {
            FileStatus[] inputFiles = local.listStatus(localInputDir);
            PrintWriter pw = new PrintWriter(hdfs.create(hdfsFile));
            for (int i=0; i<inputFiles.length; i++) {
                System.out.println(inputFiles[i].getPath().getName());
                BufferedReader br = new BufferedReader(new InputStreamReader(local.open(inputFiles[i].getPath())));
                String line;
                while((line = br.readLine()) != null) {
                    System.out.println(line);
                    pw.println(line);
                }
                br.close();
            }
            pw.close();
//            FileUtil.copyMerge()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public static void Copy() throws IOException {
        Configuration conf = new Configuration();
        FileSystem hdfs  = FileSystem.get(conf);
        hdfs.copyFromLocalFile(new Path("/Users/shiyao/tmp/tmp1"), new Path("/tmp/"));
   }
}
