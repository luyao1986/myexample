package DB;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlDBConn {

    private static Logger logger = LogManager.getLogger(MysqlDBConn.class);

    // only for unit test
    public static void setConn(Connection conn) {
        MysqlDBConn.conn = conn;
    }

    private static Connection conn = null;

    private MysqlDBConn() {
    }

    private static Connection connectToMySQL() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String dburl = "jdbc:mysql://localhost:3306/example?autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(dburl, "$username", "$password");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("SET wait_timeout = 604800");
        stmt.close();
        return conn;
    }

    public static Connection getConnection() {
        if (conn == null )
            try {
                conn = connectToMySQL();
            } catch (InstantiationException e) {
                logger.error("Connect to DB error due to" + e.getStackTrace());
                throw new RuntimeException("Connect to DB error due to" + e.getStackTrace());
            } catch (IllegalAccessException e) {
                logger.error("Connect to DB error due to" + e.getStackTrace());
                throw new RuntimeException("Connect to DB error due to" + e.getStackTrace());
            } catch (ClassNotFoundException e) {
                logger.error("Connect to DB error due to" + e.getStackTrace());
                throw new RuntimeException("Connect to DB error due to" + e.getStackTrace());
            } catch (SQLException e) {
                logger.error("Connect to DB error due to" + e.getStackTrace());
                throw new RuntimeException("Connect to DB error due to" + e.getStackTrace());
            }
        logger.info("connection to MySQLDB successful");
        return conn;
    }

}

