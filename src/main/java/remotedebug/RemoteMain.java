package remotedebug;

import tool.SomeUtils;

/**
 * we can remote debug a java application by below steps:
 * 1. run java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$port -cp mygitexample-1.0-SNAPSHOT.jar remotedebug.RemoteMain
 * 2. in Intellij, choose Run->"edit configuration"->"+"->Remote->"update the $port as above"->start the debug
 */
public class RemoteMain {
    public static void main(String[] args) {
        System.out.println("hello");
        int pid = SomeUtils.getPid();
        System.out.println("pid is"+pid);
    }
}
