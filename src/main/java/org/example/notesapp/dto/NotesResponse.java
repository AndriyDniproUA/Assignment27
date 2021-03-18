package org.example.notesapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;
import org.example.notesapp.entities.Note;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class NotesResponse {
    private List<Note> notes;
}
