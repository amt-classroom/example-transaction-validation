package ch.heigvd.amt.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@ApplicationScoped
public class MysqlDAO {

    @Inject
    @Named("mysql")
    private DataSource mysqlDataSource;

    public void insert() {
        try (var connection = mysqlDataSource.getConnection()) {
            connection.createStatement().execute("INSERT INTO mysql VALUES (1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
