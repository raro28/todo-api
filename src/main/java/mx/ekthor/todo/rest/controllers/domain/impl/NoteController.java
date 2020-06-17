package mx.ekthor.todo.rest.controllers.domain.impl;

import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntityModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.repositories.jpa.NoteRepository;
import mx.ekthor.todo.rest.controllers.domain.NoteApi;
import mx.ekthor.todo.rest.models.NoteModel;

@RestController
public class NoteController implements NoteApi{

    private NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    @Override
    public ResponseEntity<Void> notesIdDelete(final int id) {
        noteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<NoteModel> notesIdGet(final int id) {
        return ResponseEntity.ok().body(toEntityModel(noteRepository.findById(id).get()));
    }

    @Override
    public ResponseEntity<Void> notesIdPut(final int id, final NoteModel note) {
        final Note entity = noteRepository.findById(id).get();
        entity.setContent(note.getContent());

        noteRepository.save(entity);
        return ResponseEntity.noContent().build();
    }
    
}