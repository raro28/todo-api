package mx.ekthor.todo.rest.controllers.domain;

import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntity;
import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntityModel;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.repositories.jpa.NoteRepository;
import mx.ekthor.todo.persistence.repositories.jpa.TaskRepository;
import mx.ekthor.todo.rest.models.NoteEntityModel;
import mx.ekthor.todo.rest.models.NoteModel;
import mx.ekthor.todo.rest.models.TaskModel;
import mx.ekthor.todo.rest.models.responses.DataResult;
import mx.ekthor.todo.rest.models.responses.EntityResult;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskRepository taskRepository;
    private NoteRepository noteRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, NoteRepository noteRepository){
        this.taskRepository = taskRepository;
        this.noteRepository = noteRepository;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> tasksIdDelete(@PathVariable final int id) {
        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> tasksIdGet(@PathVariable final int id) {
        return ResponseEntity.ok().body(toEntityModel(taskRepository.findById(id).get()));
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<DataResult<NoteEntityModel>> tasksIdNotesGet(@PathVariable final int id, @RequestParam final int page, @RequestParam final int size) {
        if(!taskRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

        final Task task = Task.builder().id(id).build();

        DataResult<NoteEntityModel> result = DataResult
            .<NoteEntityModel>builder()
                .data(StreamSupport
                    .stream(noteRepository.findByTask(task).spliterator(), false)
                        .map(n -> toEntityModel(n))
                    .collect(Collectors.toList()))
            .build();
        result.setTotal(result.getData().size());
        
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{id}/notes")
    public ResponseEntity<EntityResult> tasksIdNotesPost(@PathVariable final int id, @RequestBody final NoteModel note) {
        if(!taskRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

        Note entity = toEntity(note, Note.class);
        entity.setTask(Task.builder().id(id).build());

        final Note storedEntity = noteRepository.save(entity);
        final EntityResult result = EntityResult.builder().id(storedEntity.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> tasksIdPut(@PathVariable final int id, @RequestBody final TaskModel task) {
        final Task entity = taskRepository.findById(id).get();
        entity.setTitle(task.getTitle());
        entity.setCompleted(task.isCompleted());

        taskRepository.save(entity);
        return ResponseEntity.noContent().build();
    }
    
}