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

public class ViewSingleNoteServlet extends JsonServlet {
    private final NotesRepository repository = UberFactory.instance().getRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        int start= req.getContextPath().length()+req.getServletPath().length();
        Integer id = Integer.valueOf(req.getRequestURI().substring(start+1));


            Note n=  repository.getSingle(Integer.valueOf(id));
            NotesResponse.Note note = new NotesResponse.Note()
                    .setId(n.getId())
                    .setTitle(n.getTitle())
                    .setContents(n.getContents())
                    .setOriginDateTime(n.getOriginDateTime().format(DateTimeFormatter.ofPattern("yyyy MM dd hh:mm")));

            req.setAttribute("note", note);
        getServletContext().getRequestDispatcher("/WEB-INF/views/single_note.jsp").forward(req, resp);
    }
}
