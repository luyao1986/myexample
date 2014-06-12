package JmockitExample;

/**
 * Created by shiyao on 5/9/14.
 */
public class ExternalService1 {
    private String name;

    public static String suffix(String name) {
//        return name;
        throw new RuntimeException("Not implemented yet!");
    }

    public static String toLower(String name) {
//        return name.toLowerCase();
        throw new RuntimeException("Not implemented yet!");
    }
}
