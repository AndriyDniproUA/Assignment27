package org.example.notesapp.services;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.example.notesapp.entities.Note;
import org.example.notesapp.infrastructure.annotations.AutoWired;
import org.example.notesapp.infrastructure.annotations.Component;
import org.example.notesapp.utils.BeanPropertyRowMapper;
import org.example.notesapp.utils.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SqlNotesRepository implements NotesRepository{

    //    private final UberFactory uberFactory;
//        @AutoWired
//    UberFactory uberFactory;

//    JdbcTemplate jdbc=uberFactory.instance().getJdbcTemplate();



    private final JdbcTemplate jdbc=jdbcTemplate();

    @Override
    public List<Note> getAll() {
        return jdbc.query("SELECT id,title, contents, origin_date_time FROM notes", new BeanPropertyRowMapper<>(Note.class));
    }

    @Override
    public Note getSingle(int id) {
        return jdbc.queryOne("SELECT id,title, contents, origin_date_time FROM notes WHERE id=?" ,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Note.class));
    }


    @Override
    public void add(Note n) {
        jdbc.update(
                "INSERT INTO notes (title, contents) VALUES (?,?)",
                new Object[]{n.getTitle(),n.getContents()}
        );
    }

    @Override
    public void delete(int id) {
        jdbc.update(
                "DELETE FROM notes WHERE id=?",
                new Object[]{id}
        );



    }

    private JdbcTemplate jdbcTemplate(){
        {String dsn = "jdbc:postgresql://localhost:5432/notes_repository";
            String user = "postgres";
            String password = "1234";

            HikariConfig config = new HikariConfig();
            config.setDriverClassName(org.postgresql.Driver.class.getName());
            config.setJdbcUrl(dsn);
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(8);
            config.setMinimumIdle(4);

            DataSource dataSource = new HikariDataSource(config);
            return new JdbcTemplate(dataSource);}
    }

}
