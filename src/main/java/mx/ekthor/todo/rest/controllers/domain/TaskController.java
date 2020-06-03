package mx.ekthor.todo.rest.controllers.domain;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import mx.ekthor.openapi.api.TasksApi;
import mx.ekthor.openapi.models.Entity;
import mx.ekthor.openapi.models.Note;
import mx.ekthor.openapi.models.NoteResult;
import mx.ekthor.openapi.models.Task;
import mx.ekthor.todo.persistence.repositories.jpa.NoteRepository;
import mx.ekthor.todo.persistence.repositories.jpa.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController implements TasksApi {

    private TaskRepository taskRepository;
    private NoteRepository noteRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, NoteRepository noteRepository){
        this.taskRepository = taskRepository;
        this.noteRepository = noteRepository;
    }

    private Task toDto(mx.ekthor.todo.persistence.domain.jpa.Task task) {
        Task result = new Task();
        result.setIsCompleted(task.isCompleted());
        result.setTitle(task.getTitle());

        return result;
    }

    private mx.ekthor.openapi.models.NoteEntity toDtoEntity(final mx.ekthor.todo.persistence.domain.jpa.Note item) {
        mx.ekthor.openapi.models.NoteEntity result = new mx.ekthor.openapi.models.NoteEntity();
        result.setContent(item.getContent());
        result.setId(item.getId());

        return result;
    }

    @Override
    public ResponseEntity<Void> tasksIdDelete(Integer id) {
        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Task> tasksIdGet(Integer id) {
        return ResponseEntity.ok().body(toDto(taskRepository.findById(id).get()));
    }

    @Override
    public ResponseEntity<NoteResult> tasksIdNotesGet(Integer id, @Valid Integer page, @Valid Integer size) {
        final mx.ekthor.todo.persistence.domain.jpa.Task task = new mx.ekthor.todo.persistence.domain.jpa.Task();
        task.setId(id);


        NoteResult result = new NoteResult();
        result.setData(StreamSupport.stream(noteRepository.findByTask(task).spliterator(), false).map(item -> toDtoEntity(item)).collect(Collectors.toList()));
        result.setTotal(result.getData().size());
        
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Entity> tasksIdNotesPost(Integer id, @Valid Note note) {
        final mx.ekthor.todo.persistence.domain.jpa.Task task = new mx.ekthor.todo.persistence.domain.jpa.Task();
        task.setId(id);

        final mx.ekthor.todo.persistence.domain.jpa.Note entity = new mx.ekthor.todo.persistence.domain.jpa.Note();
        entity.setContent(note.getContent());
        entity.setTask(task);

        final mx.ekthor.todo.persistence.domain.jpa.Note storedEntity = noteRepository.save(entity);
        final Entity result = new Entity();
        result.setId(storedEntity.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Void> tasksIdPut(Integer id, @Valid Task task) {
        final mx.ekthor.todo.persistence.domain.jpa.Task entity = taskRepository.findById(id).get();
        entity.setTitle(task.getTitle());
        entity.setCompleted(task.getIsCompleted());

        taskRepository.save(entity);
        return ResponseEntity.noContent().build();
    }
    
}