package controls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bdcfg {
    public static final String DEFAULT_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String DEFAULT_URL = "jdbc:mysql://localhost/tpprog";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "ryzenerf5";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DEFAULT_DRIVER_CLASS);
        return DriverManager.getConnection(DEFAULT_URL,DEFAULT_USERNAME, DEFAULT_PASSWORD);

    }

}
