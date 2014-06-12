package tool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

public class FileOperation {
    /**
     * read content in hdfs inputFile or inputDir/* and write to hdfs outputFile
     * @param inputDirOrFile
     * @param outputFile
     * @throws java.io.IOException
     */
    public static void ReadWriteHdfs(String inputDirOrFile, String outputFile) throws IOException {
        Configuration conf = new Configuration();
        System.out.println("conf is "+conf.toString());
        //FileSystem can be local or hdfs
        FileSystem hdfs  = FileSystem.get(conf);
        //Path to encode file/directory and FileStatus to store metadata
        Path localInputDir = new Path(inputDirOrFile);
        Path hdfsFile = new Path(outputFile);
        try {
            FileStatus[] inputFiles = hdfs.listStatus(localInputDir);
            PrintWriter pw = new PrintWriter(hdfs.create(hdfsFile));
            for (int i=0; i<inputFiles.length; i++) {
                System.out.println(inputFiles[i].getPath().getName());
                BufferedReader br = new BufferedReader(new InputStreamReader(hdfs.open(inputFiles[i].getPath())));
                String line;
                while((line = br.readLine()) != null) {
                    System.out.println(line);
                    pw.println(line);
                }
                br.close();
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLocalFileByHadoopLocalFS(String inputDirOrFile) throws IOException {
        Configuration conf = new Configuration();
        System.out.println("conf is "+conf.toString());
        FileSystem local = FileSystem.getLocal(conf);
        local.deleteOnExit(new Path(inputDirOrFile));
    }

    public static void deleteLocalFileByLocalFS(String inputDirOrFile) throws IOException {
        File file = new File(inputDirOrFile);
        if(file.exists()) {
            System.out.println("delete file:"+file.getAbsoluteFile());
            file.delete();
        }
        else {
            System.out.println("file:"+file.getAbsoluteFile()+" does not exist");
        }
    }

}
