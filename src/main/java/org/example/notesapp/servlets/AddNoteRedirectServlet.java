package org.example.notesapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.notesapp.dto.NotesResponse;
import org.example.notesapp.entities.Note;
import org.example.notesapp.services.NotesRepository;
import org.example.notesapp.services.UberFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AddNoteRedirectServlet extends JsonServlet {
    private final NotesRepository repository = UberFactory.instance().getRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/add_note.jsp").forward(req, resp);
    }
}
