package org.example.notesapp.services;

import lombok.RequiredArgsConstructor;
import org.example.notesapp.entities.Note;
import org.example.notesapp.utils.BeanPropertyRowMapper;
import org.example.notesapp.utils.JdbcTemplate;

import java.util.List;

@RequiredArgsConstructor
public class SqlNotesRepository implements NotesRepository{
    private final JdbcTemplate jdbc;

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
}
