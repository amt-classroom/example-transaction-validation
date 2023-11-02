package ch.heigvd.amt.transaction;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;

@ApplicationScoped
public class MariaDAO {

    @Inject
    @Named("maria")
    private DataSource mariaDataSource;

    public void insert() {
        try (var connection = mariaDataSource.getConnection()) {
            connection.createStatement().execute("INSERT INTO maria VALUES (1)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
