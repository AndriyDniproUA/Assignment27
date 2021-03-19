package org.example.notesapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.notesapp.dto.NoteInput;
import org.example.notesapp.dto.NoteOutput;
import org.example.notesapp.entities.Note;
import org.example.notesapp.dto.NotesResponse;
import org.example.notesapp.services.NotesRepository;
import org.example.notesapp.services.UberFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAddNotesServlet extends JsonServlet {
    private final NotesRepository repository = UberFactory.instance().getRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //List<Note> notes = repository.getAll();


//        return response.getContacts().stream().map(c -> {
//            Contact contact = new Contact();
//            contact.setId(c.getId());
//            contact.setName(c.getName());
//            contact.setType(Contact.ContactType.valueOf(c.getType().toUpperCase()));
//            contact.setValue(c.getValue());
//            return contact;
//        }).collect(Collectors.toList());

        List<NotesResponse.Note> response = repository.getAll().stream().map(n -> {
            return new NotesResponse.Note()
                    .setId(n.getId())
                    .setTitle(n.getTitle())
                    .setContents(n.getContents())
                    .setOriginDateTime(n.getOriginDateTime().format(DateTimeFormatter.ofPattern("yyyy MM dd hh:mm")));
        }).collect(Collectors.toList());

        writeJson(response, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteInput noteInput = readJson(NoteInput.class, req);
        Note note = new Note().setTitle(noteInput.getTitle()).setContents(noteInput.getContents());
        repository.add(note);
    }
}
