package ch.heigvd.amt.transaction;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;

@ApplicationScoped
public class TransactionService {

    @Inject
    private EntityManager em;

    @Transactional
    public void entityManagerTransaction() {
        em.persist(new PostgresEntity(1));
    }

    @Inject
    @Named("mysql")
    private DataSource mysqlDataSource;

    public void mysqlDataSourceTransaction() {
        try {
            Connection connection = mysqlDataSource.getConnection();
            connection.setAutoCommit(false);
            connection.createStatement().execute("INSERT INTO mysql VALUES (1)");
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject
    @Named("maria")
    private DataSource mariaDataSource;

    public void mariaDataSourceTransaction() {
        try {
            Connection connection = mariaDataSource.getConnection();
            connection.setAutoCommit(false);
            connection.createStatement().execute("INSERT INTO maria VALUES (1)");
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
