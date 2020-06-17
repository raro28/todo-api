package mx.ekthor.todo.rest.controllers.domain;

import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntityModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.repositories.jpa.NoteRepository;
import mx.ekthor.todo.rest.models.NoteModel;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private NoteRepository noteRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> notesIdDelete(@PathVariable final int id) {
        noteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteModel> notesIdGet(@PathVariable final int id) {
        return ResponseEntity.ok().body(toEntityModel(noteRepository.findById(id).get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> notesIdPut(@PathVariable final int id, @RequestBody final NoteModel note) {
        final Note entity = noteRepository.findById(id).get();
        entity.setContent(note.getContent());

        noteRepository.save(entity);
        return ResponseEntity.noContent().build();
    }
    
}