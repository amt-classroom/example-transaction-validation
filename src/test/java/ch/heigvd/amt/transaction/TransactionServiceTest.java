package ch.heigvd.amt.transaction;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class TransactionServiceTest {

    @Inject
    EntityManager entityManager;

    @Inject
    @Named("mysql")
    DataSource mysqlDataSource;

    @Inject
    @Named("maria")
    DataSource mariaDataSource;

    @Inject
    TransactionService transactionService;

    @Test
    void entityManagerTransaction() {
        try {
            transactionService.entityManagerTransaction();
        } catch (Exception e) {
            // Ignore
        }
        assertNotNull(entityManager.find(PostgresEntity.class, 1L));
    }

    @Test
    void mysqlDataSourceTransaction() {
        try {
            transactionService.mysqlDataSourceTransaction();
        } catch (Exception e) {
            // Ignore
        }
        try (var connection = mysqlDataSource.getConnection()) {
            // connection.setTransactionIsolation(Connection.TRANSACTION_NONE);
            // connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            // connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            // connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM mysql");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertEquals(1, count);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void postgresDataSourceTransaction() {
        try {
            transactionService.mariaDataSourceTransaction();
        } catch (Exception e) {
            // Ignore
        }
        try (var connection = mariaDataSource.getConnection()) {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM maria");
            int count = 0;
            while (resultSet.next()) {
                count++;
            }
            assertEquals(1, count);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}