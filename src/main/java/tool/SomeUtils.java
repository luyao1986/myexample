package tool;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by shiyao on 4/24/14.
 */
public class SomeUtils {
    private static int pid=-1;

    public static int getPid() {
        if(pid == -1) {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String name = runtime.getName(); // format: "pid@hostname"
            try {
                return Integer.parseInt(name.substring(0, name.indexOf('@')));
            } catch (Exception e) {
                return -1;
            }
        }
        else {
            return pid;
        }
    }
}
