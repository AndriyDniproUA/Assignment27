package org.example.notesapp.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.notesapp.utils.JdbcTemplate;

import javax.sql.DataSource;

public class UberFactory {
    private final static UberFactory INSTANCE = new UberFactory();
    private final NotesRepository repository;

    private UberFactory() {
        this.repository = new SqlNotesRepository(jdbcTemplate());
    }


    public static UberFactory instance() {
        return INSTANCE;
    }

    public NotesRepository getRepository() {
        return repository;
    }

    private JdbcTemplate jdbcTemplate(){
        {String dsn = "jdbc:postgresql://localhost:5432/notes_repository";
            String user = "postgres";
            String password = "1234";

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dsn);
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(8);
            config.setMinimumIdle(4);

            DataSource dataSource = new HikariDataSource(config);
            return new JdbcTemplate(dataSource);}
    }
}
