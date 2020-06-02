package mx.ekthor.todo.rest.controllers.domain;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.openapi.api.NotesApi;
import mx.ekthor.openapi.models.Note;
import mx.ekthor.todo.persistence.repositories.jpa.NoteRepository;

@RestController
public class NoteController implements NotesApi {

    private NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    private Note toDto(mx.ekthor.todo.persistence.domain.jpa.Note note) {
        Note result = new Note();
        result.setContent(note.getContent());

        return result;
    }

    @Override
    public ResponseEntity<Void> notesIdDelete(Integer id) {
        noteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Note> notesIdGet(Integer id) {
        return ResponseEntity.ok().body(toDto(noteRepository.findById(id).get()));
    }

    @Override
    public ResponseEntity<Void> notesIdPut(Integer id, @Valid Note note) {
        final mx.ekthor.todo.persistence.domain.jpa.Note entity = noteRepository.findById(id).get();
        entity.setContent(note.getContent());

        noteRepository.save(entity);
        return ResponseEntity.noContent().build();
    }
    
}