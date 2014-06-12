package instrumentation;

/**
 * Created by shiyao on 5/24/14.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Welcome.welcome();
        int count = 0;
        while (true) {
            Thread.sleep(500);
            count++;
            Welcome.welcome();
            if (count >= 5) {
                break;
            }
        }
    }
}

class Welcome {
    public static void welcome() {
        System.out.println("welcome, yahoo");
    }
}

class WelcomeNew {
    public static void welcome() {
        System.out.println("welcome, new yahoo");
    }
}
