package DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;

/**
 * Created by shiyao on 4/10/14.
 */
public class DerbyTestUtil {
    private static final String DEFAULT_DELIMITER = ";";

    public static Connection getConn() throws IOException, SQLException, ClassNotFoundException {
        if(conn == null)
        {
            setupDataSources();
        }
        return conn;
    }

    private static Connection conn = null;

    private static void setupDataSources() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("setup derby database");
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection("jdbc:derby:memory:unittest;create=true");
        runScript(new FileReader("src/main/resources/DB/initialDB.sql"));
        //above will create some tables; we can inital some table if we wanted here or in the test class
    }

    public static void executeSQLCommands(String commands) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("execute SQL Command: " + commands);
        runScript(new BufferedReader(new StringReader(commands)));
    }

    public static void runScript(FileReader br) throws IOException, SQLException, ClassNotFoundException {
        runScript(new BufferedReader(br));
    }

    public static void runScript(BufferedReader br) throws IOException, SQLException, ClassNotFoundException {
        StringBuffer command = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            String trimmedLine = line.trim();
            System.out.println("run: " + trimmedLine);
            if (trimmedLine.startsWith("--") || trimmedLine.startsWith("//")) {
            }
            else if (trimmedLine.endsWith(DEFAULT_DELIMITER)) {
                command.append(line.substring(0, line.lastIndexOf(DEFAULT_DELIMITER)) + " ");
                Statement statement = getConn().createStatement();
                boolean hasResults = statement.execute(command.toString());
                getConn().commit();
                ResultSet rs = statement.getResultSet();
                if (hasResults && rs != null) {
                    ResultSetMetaData md = rs.getMetaData();
                    for (int i = 0; i < md.getColumnCount(); i++) {
                        String name = md.getColumnLabel(i);
                    }
                }
                command.setLength(0);
                statement.close();
            } else {
                command.append(line+" ");
            }
        }
        br.close();
    }

    public static void cleanDerbyDB() throws SQLException {
        try {
            DriverManager.getConnection("jdbc:derby:memory:unittest;drop=true");
            conn = null;
        }
        catch (Exception e) {
            System.out.println("done clean db"); //it's tricky
        }
    }
}
