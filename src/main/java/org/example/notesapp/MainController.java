package org.example.notesapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.notesapp.dto.NotesResponse;
import org.example.notesapp.entities.Note;
import org.example.notesapp.infrastructure.annotations.AutoWired;
import org.example.notesapp.infrastructure.annotations.Controller;
import org.example.notesapp.infrastructure.annotations.GetMapping;
import org.example.notesapp.infrastructure.annotations.PostMapping;
import org.example.notesapp.services.NotesRepository;
import org.example.notesapp.services.UberFactory;
import org.example.notesapp.servlets.JsonController;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController extends JsonController {

    @AutoWired
    public NotesRepository repository;


    @GetMapping("/")
    public void displayNotesList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        List<NotesResponse.Note> notes = repository.getAll().stream().map(n -> {
            return new NotesResponse.Note()
                    .setId(n.getId())
                    .setTitle(n.getTitle())
                    .setContents(n.getContents())
                    .setOriginDateTime(n.getOriginDateTime().format(DateTimeFormatter.ofPattern("yyyy MM dd hh:mm")));
        }).collect(Collectors.toList());

        req.setAttribute("notes", notes);
        req.getRequestDispatcher("/WEB-INF/views/notes_list.jsp").forward(req, resp);
    }


    @GetMapping("/notes/{id}")
    public void displaySingleNote(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Map<String,String> params = (Map<String, String>)req.getAttribute("parameters");


        System.out.println("ID:"+params.get("id"));
        Integer id = Integer.valueOf(params.get("id"));

        Note n=  repository.getSingle(id);
            NotesResponse.Note note = new NotesResponse.Note()
                    .setId(n.getId())
                    .setTitle(n.getTitle())
                    .setContents(n.getContents())
                    .setOriginDateTime(n.getOriginDateTime().format(DateTimeFormatter.ofPattern("yyyy MM dd hh:mm")));

            req.setAttribute("note", note);
        req.getRequestDispatcher("/WEB-INF/views/single_note.jsp").forward(req, resp);
    }


    @GetMapping("/addnote")
    public void addNoteJspRedirect (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/views/add_note.jsp").forward(req, resp);
    }


    @PostMapping("/addnote")
    public void addNewNote (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Note note = new Note()
                .setTitle(req.getParameter("title"))
                .setContents(req.getParameter("contents"));
        repository.add(note);
        resp.sendRedirect(req.getContextPath()+"/");
    }

    @PostMapping("/delete")
    public void deleteNote(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        repository.delete(Integer.valueOf(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath()+"/");
    }


}
