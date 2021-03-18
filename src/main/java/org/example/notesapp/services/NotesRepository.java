package org.example.notesapp.services;

import org.example.notesapp.entities.Note;

import java.util.List;

public interface NotesRepository {
    List<Note> getAll();
    void add(Note n);
    void delete(int id);
}
