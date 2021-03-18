package org.example.notesapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.notesapp.dto.NoteInput;
import org.example.notesapp.dto.NotesResponse;
import org.example.notesapp.entities.Note;
import org.example.notesapp.services.NotesRepository;
import org.example.notesapp.services.UberFactory;

import java.io.IOException;
import java.util.List;

public class DeleteNoteServlet extends JsonServlet {
    private final NotesRepository repository = UberFactory.instance().getRepository();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            int idInt= Integer.valueOf(id);
            repository.delete(idInt);
        }
    }
}
