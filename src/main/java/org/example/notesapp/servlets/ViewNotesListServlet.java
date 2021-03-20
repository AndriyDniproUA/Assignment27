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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewNotesListServlet extends JsonServlet {
    private final NotesRepository repository = UberFactory.instance().getRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        List<NotesResponse.Note>notes = repository.getAll().stream().map(n -> {
            return new NotesResponse.Note()
                    .setId(n.getId())
                    .setTitle(n.getTitle())
                    .setContents(n.getContents())
                    .setOriginDateTime(n.getOriginDateTime().format(DateTimeFormatter.ofPattern("yyyy MM dd hh:mm")));
        }).collect(Collectors.toList());

        req.setAttribute("notes", notes);
        getServletContext().getRequestDispatcher("/WEB-INF/views/notes_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Note note = new Note()
                .setTitle(req.getParameter("title"))
                .setContents(req.getParameter("contents"));
        repository.add(note);
        resp.sendRedirect(req.getContextPath()+"/");

    }
}
