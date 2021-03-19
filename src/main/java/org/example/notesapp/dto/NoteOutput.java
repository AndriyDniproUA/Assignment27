package org.example.notesapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class NoteOutput {
    private Integer id;
    private String title;
    private String contents;
    private String originDateTime;
}
