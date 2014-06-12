package DB;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by shiyao on 4/12/14.
 */
public class ReportLinesTest {
    String sqlInitReportLinesCommands =
            "INSERT INTO REPORTLINES (REPORTID, LINE) VALUES (1, '123');\n"+
                    "INSERT INTO REPORTLINES (REPORTID, LINE) VALUES (1, '456');\n";


    @Before
    public void setUp() throws Exception {
        MysqlDBConn.setConn(DerbyTestUtil.getConn());
        DerbyTestUtil.executeSQLCommands(sqlInitReportLinesCommands);
    }

    @After
    public void tearDown() throws Exception {
        DerbyTestUtil.cleanDerbyDB();
    }

    @Test
    public void testGetReportLines() throws Exception {
        ArrayList<String> lines = ReportLines.getReportLines(new Long(1));
        Assert.assertEquals(lines.size(), 2);
    }
}
