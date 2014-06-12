package pig;


import org.apache.pig.pigunit.PigTest;
import org.apache.pig.tools.parameters.ParseException;
import org.junit.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;

public class WordCounterTest {
    private String gen(char del, String... fields) {
        String line = null;
        for (String f : fields) {
            if (line != null) {
                line += del;
            } else {
                line = "";
            }
            line += f;
        }
        if (del == ',') {
            line = "(" + line + ")";
        }
        return line;
    }

    @Test
    @Ignore
    public void test() throws IOException, ParseException {
        String[] args = {
                "InputParam=input_stub",
                "OutputParam=output_stub",
        };

        String[] input  = {
                "this is a simple test case",
                "this is a simple test case",
        };
        String[] output = {
                gen(',', "a", "2"),
                gen(',', "case", "2"),
                gen(',', "is", "2"),
                gen(',', "simple", "2"),
                gen(',', "test", "2"),
                gen(',', "this", "2"),
        };

        String pigfile = this.getClass().getResource("/WordCounter.pig").getFile().toString();
        PigTest pigTest = new PigTest(pigfile, args);
        pigTest.assertOutput("RAW_LINE", input, "WORD_COUNT_A", output);
    }
}

