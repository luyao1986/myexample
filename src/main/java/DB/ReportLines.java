package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by shiyao on 4/10/14.
 */
public class ReportLines {
    private Long reportid;
    private String line;

    private ReportLines(Long reportid, String id) {
        this.line = id;
        this.reportid = reportid;
    }

    public static ArrayList<String> getReportLines(Long reportID) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            PreparedStatement stmt = MysqlDBConn.getConnection().prepareStatement(
                    "select LINE from REPORTLINES where REPORTID=? \n");
            stmt.setLong(1, reportID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                lines.add(rs.getString("LINE"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}


