package org.example.notesapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.notesapp.dto.NoteInput;
import org.example.notesapp.entities.Note;
import org.example.notesapp.dto.NotesResponse;
import org.example.notesapp.services.NotesRepository;
import org.example.notesapp.services.UberFactory;

import java.io.IOException;
import java.util.List;

public class ViewAddNotesServlet extends JsonServlet {
    private final NotesRepository repository = UberFactory.instance().getRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Note> notes = repository.getAll();
        NotesResponse response = new NotesResponse().setNotes(notes);

        writeJson(response, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteInput noteInput = readJson(NoteInput.class, req);
        Note note = new Note().setTitle(noteInput.getTitle()).setContents(noteInput.getContents());
        repository.add(note);
    }
}
