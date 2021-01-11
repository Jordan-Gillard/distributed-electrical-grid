package database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/electricalGridDB";
    static final String USER = "root";
    static final String PASS = "example1";


    public static void createEmptyBatteryEventTable(Jdbi jdbi) {
        String dropBatteryEventTable = "DROP TABLE IF EXISTS batteryEvent";
        String dropChargingEventTable = "DROP TABLE IF EXISTS chargingEvent";
        String createChargingEventTable =
            "CREATE TABLE IF NOT EXISTS chargingEvent "
                + "(device_id VARCHAR(255),"  + "charging int,"
                + "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," + "PRIMARY KEY (device_id))";
        jdbi.useHandle(handle -> {
            handle.execute(dropBatteryEventTable);
            handle.execute(dropChargingEventTable);
            handle.execute(createChargingEventTable);

        });
    }


    public static Jdbi getJdbiConnectorWithDataSource() {
        BasicDataSource dataSource = getBasicDataSource();
        return Jdbi.create(dataSource);
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
