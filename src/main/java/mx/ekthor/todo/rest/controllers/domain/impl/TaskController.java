package mx.ekthor.todo.rest.controllers.domain.impl;

import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntity;
import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntityModel;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.repositories.jpa.NoteRepository;
import mx.ekthor.todo.persistence.repositories.jpa.TaskRepository;
import mx.ekthor.todo.rest.controllers.domain.TaskApi;
import mx.ekthor.todo.rest.models.NoteEntityModel;
import mx.ekthor.todo.rest.models.NoteModel;
import mx.ekthor.todo.rest.models.TaskModel;
import mx.ekthor.todo.rest.models.responses.DataResult;
import mx.ekthor.todo.rest.models.responses.EntityResult;

@RestController
public class TaskController implements TaskApi{

    private TaskRepository taskRepository;
    private NoteRepository noteRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, NoteRepository noteRepository){
        this.taskRepository = taskRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public ResponseEntity<Void> tasksIdDelete(final int id) {
        taskRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskModel> tasksIdGet(final int id) {
        return ResponseEntity.ok().body(toEntityModel(taskRepository.findById(id).get()));
    }

    @Override
    public ResponseEntity<DataResult<NoteEntityModel>> tasksIdNotesGet(final int id, final int page, final int size) {
        if(!taskRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

        final Task task = Task.builder().id(id).build();
        Page<Note> pageResult = noteRepository.findByTask(task, PageRequest.of(page - 1, size));

        DataResult<NoteEntityModel> result = DataResult
            .<NoteEntityModel>builder()
                .data(pageResult
                    .stream()
                        .map(n -> toEntityModel(n))
                    .collect(Collectors.toList()))
            .build();
        result.setTotal(pageResult.getTotalElements());
        result.setPages(pageResult.getTotalPages());
        
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<EntityResult> tasksIdNotesPost(final int id, final NoteModel note) {
        if(!taskRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

        Note entity = toEntity(note, Note.class);
        entity.setTask(Task.builder().id(id).build());

        final Note storedEntity = noteRepository.save(entity);
        final EntityResult result = EntityResult.builder().id(storedEntity.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Void> tasksIdPut(final int id, final TaskModel task) {
        final Task entity = taskRepository.findById(id).get();
        entity.setTitle(task.getTitle());
        entity.setCompleted(task.isCompleted());

        taskRepository.save(entity);
        return ResponseEntity.noContent().build();
    }
    
}