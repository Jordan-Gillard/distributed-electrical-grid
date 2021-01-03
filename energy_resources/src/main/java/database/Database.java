package database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/electricalGridDB";
    static final String USER = "root";
    static final String PASS = "example1";


    public static void main(String[] args) {
        Jdbi jdbi = getJdbiConnectorWithDataSource();
        String dropTable = "DROP TABLE IF EXISTS batteryEvent";
        String sql =
            "CREATE TABLE IF NOT EXISTS batteryEvent " + "(batteryEventID INTEGER AUTO_INCREMENT," + "charging_source VARCHAR(255)," + "processor4_temp int," + "device_id VARCHAR(255)," + "processor2_temp int," + "processor1_temp int," + "charging int," + "current_capacity int," + "inverter_state int," + "moduleL_temp int," + "moduleR_temp int," + "processor3_temp int," + "soC_regulator float," + "time timestamp," + "PRIMARY KEY (batteryEventID))";
        jdbi.useHandle(handle -> {
            handle.execute(dropTable);
            handle.execute(sql);
            handle.execute(
                "INSERT INTO electricalGridDB.batteryEvent(charging_source, charging) VALUES (?, ?)",
                "beach", 11);
        });
    }


    public static Jdbi getJdbiConnectorWithDataSource() {
        BasicDataSource dataSource = getBasicDataSource();
        Jdbi jdbi = Jdbi.create(dataSource);
        return jdbi;
    }


    private static BasicDataSource getBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        dataSource.setUrl(DB_URL);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

}
