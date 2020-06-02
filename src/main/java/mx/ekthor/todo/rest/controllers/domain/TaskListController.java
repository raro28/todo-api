package mx.ekthor.todo.rest.controllers.domain;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.openapi.api.ListsApi;
import mx.ekthor.openapi.models.Entity;
import mx.ekthor.openapi.models.Task;
import mx.ekthor.openapi.models.TaskList;
import mx.ekthor.openapi.models.TaskListResult;
import mx.ekthor.openapi.models.TaskResult;
import mx.ekthor.todo.persistence.repositories.jpa.TaskListRepository;
import mx.ekthor.todo.persistence.repositories.jpa.TaskRepository;

@RestController
public class TaskListController implements ListsApi {

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskListController(final TaskListRepository repository, TaskRepository taskRepository) {
        this.taskListRepository = repository;
        this.taskRepository = taskRepository;
    }

    private mx.ekthor.openapi.models.TaskListEntity toDtoEntity(final mx.ekthor.todo.persistence.domain.jpa.TaskList item){
        final mx.ekthor.openapi.models.TaskListEntity mapped = new mx.ekthor.openapi.models.TaskListEntity();
        mapped.setId(item.getId());
        mapped.setTitle(item.getTitle());

        return mapped;
    }

    private mx.ekthor.openapi.models.TaskEntity toDtoEntity(final mx.ekthor.todo.persistence.domain.jpa.Task item){
        final mx.ekthor.openapi.models.TaskEntity mapped = new mx.ekthor.openapi.models.TaskEntity();
        mapped.setId(item.getId());
        mapped.setTitle(item.getTitle());
        mapped.setIsCompleted(item.isCompleted());

        return mapped;
    }

    private mx.ekthor.openapi.models.TaskList toDto(final mx.ekthor.todo.persistence.domain.jpa.TaskList item){
        final mx.ekthor.openapi.models.TaskList mapped = new mx.ekthor.openapi.models.TaskList();
        mapped.setTitle(item.getTitle());

        return mapped;
    }

    @Override
    public ResponseEntity<TaskListResult> listsGet(@Valid final Integer page, @Valid final Integer size) {
        final TaskListResult result = new TaskListResult();
        result.setData(StreamSupport.stream(taskListRepository.findAll().spliterator(), false).map(item -> toDtoEntity(item)).collect(Collectors.toList()));
        result.setTotal(result.getData().size());
        
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> listsIdDelete(final Integer id) {
        taskListRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskList> listsIdGet(final Integer id) {
        return ResponseEntity.ok().body(toDto(taskListRepository.findById(id).get()));
    }

    @Override
    public ResponseEntity<Void> listsIdPut(final Integer id, @Valid final TaskList taskList) {
        final mx.ekthor.todo.persistence.domain.jpa.TaskList entity = new mx.ekthor.todo.persistence.domain.jpa.TaskList();
        entity.setId(id);
        entity.setTitle(taskList.getTitle());

        taskListRepository.save(entity);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResult> listsIdTasksGet(final Integer id, @Valid final Integer page, @Valid final Integer size) {
        final mx.ekthor.todo.persistence.domain.jpa.TaskList taskList = new mx.ekthor.todo.persistence.domain.jpa.TaskList();
        taskList.setId(id);

        final TaskResult result = new TaskResult();
        result.setData(StreamSupport.stream(taskRepository.findByTaskList(taskList).spliterator(), false).map(item -> toDtoEntity(item)).collect(Collectors.toList()));
        result.setTotal(result.getData().size());
        
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Entity> listsIdTasksPost(final Integer id, @Valid final Task task) {
        final mx.ekthor.todo.persistence.domain.jpa.TaskList taskList = new mx.ekthor.todo.persistence.domain.jpa.TaskList();
        taskList.setId(id);

        final mx.ekthor.todo.persistence.domain.jpa.Task entity = new mx.ekthor.todo.persistence.domain.jpa.Task();
        entity.setTitle(task.getTitle());
        entity.setCompleted(task.getIsCompleted());
        entity.setTaskList(taskList);

        final mx.ekthor.todo.persistence.domain.jpa.Task storedEntity = taskRepository.save(entity);
        final Entity result = new Entity();
        result.setId(storedEntity.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<Entity> listsPost(@Valid final TaskList taskList) {
        final mx.ekthor.todo.persistence.domain.jpa.TaskList entity = new mx.ekthor.todo.persistence.domain.jpa.TaskList();
        entity.setTitle(taskList.getTitle());

        final mx.ekthor.todo.persistence.domain.jpa.TaskList storedEntity = taskListRepository.save(entity);
        final Entity result = new Entity();
        result.setId(storedEntity.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
}