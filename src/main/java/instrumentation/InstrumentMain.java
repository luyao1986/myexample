package instrumentation;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by shiyao on 5/24/14.
 */
public class InstrumentMain {
//    public static void agentmain (String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
//TODO not work yet
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new WelcomeTransformer(), true);
//        inst.retransformClasses(Welcome.class);
        System.out.println("Agent Main Done");
    }
}

class WelcomeTransformer implements ClassFileTransformer {
    //transform 方法则完成了类定义的替换转换,all future class definitions will be seen by the transformer.The transformer is called when classes are loaded, when they are redefined
    public byte[] transform(ClassLoader l, String className, Class<?> c, ProtectionDomain pd, byte[] b) throws IllegalClassFormatException {
        if (!className.equals("Welcome")) {
            System.out.println("no need to transfer:"+className);
            return null;
        }
        try {
            System.out.println("try transform:"+className);
            return IOUtils.toByteArray(new FileInputStream(new File("WelcomeNew.class")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
