package database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jdbi.v3.core.Jdbi;

public class Database {
    public Jdbi getJdbiConnectorWithDataSource() {
        BasicDataSource dataSource = getBasicDataSource();
        Jdbi jdbi = Jdbi.create(dataSource);
        return jdbi;
    }

    private BasicDataSource getBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("example");
        // TODO: Create DB if it does not exist
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/electricalGridDB");
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }
}
