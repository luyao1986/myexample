package JmockitExample;

/**
 * Created by shiyao on 5/19/14.
 */
public final class ExternalService {

    public final String getSomething() {
        throw new RuntimeException("not implemented yet");
    }

    public static String getDetails(String name) {
        throw new RuntimeException("not implemented yet");
    }
}
