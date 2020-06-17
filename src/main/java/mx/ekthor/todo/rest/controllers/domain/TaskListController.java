package mx.ekthor.todo.rest.controllers.domain;

import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntity;
import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntityModel;

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

import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.domain.jpa.TaskList;
import mx.ekthor.todo.persistence.repositories.jpa.TaskListRepository;
import mx.ekthor.todo.persistence.repositories.jpa.TaskRepository;
import mx.ekthor.todo.rest.models.TaskEntityModel;
import mx.ekthor.todo.rest.models.TaskListEntityModel;
import mx.ekthor.todo.rest.models.TaskListModel;
import mx.ekthor.todo.rest.models.TaskModel;
import mx.ekthor.todo.rest.models.responses.DataResult;
import mx.ekthor.todo.rest.models.responses.EntityResult;

@RestController
@RequestMapping("/lists")
public class TaskListController {

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskListController(final TaskListRepository repository, TaskRepository taskRepository) {
        this.taskListRepository = repository;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<DataResult<TaskListEntityModel>> listsGet(@RequestParam final int page,
            @RequestParam final int size) {
        final DataResult<TaskListEntityModel> result = DataResult.<TaskListEntityModel>builder()
                .data(StreamSupport
                    .stream(taskListRepository.findAll().spliterator(), false)
                        .map(t -> toEntityModel(t))
                    .collect(Collectors.toList()))
                .build();
        result.setTotal(result.getData().size());

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> listsIdDelete(@PathVariable final int id) {
        taskListRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskListModel> listsIdGet(@PathVariable final int id) {
        return ResponseEntity.ok().body(toEntityModel(taskListRepository.findById(id).get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> listsIdPut(@PathVariable final int id, @RequestBody final TaskListModel taskList) {
        final TaskList entity = taskListRepository.findById(id).get();
        entity.setTitle(taskList.getTitle());

        taskListRepository.save(entity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<DataResult<TaskEntityModel>> listsIdTasksGet(@PathVariable final int id, @RequestParam final int page, @RequestParam final int size) {
        final TaskList taskList = TaskList.builder().id(id).build();

        final DataResult<TaskEntityModel> result = DataResult.<TaskEntityModel>builder()
                .data(StreamSupport
                    .stream(taskRepository.findByTaskList(taskList).spliterator(), false)
                        .map(t -> toEntityModel(t))
                    .collect(Collectors.toList()))
                .build();
        result.setTotal(result.getData().size());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<EntityResult> listsIdTasksPost(@PathVariable final int id, @RequestBody final TaskModel task) {
        Task entity = toEntity(task, Task.class);
        entity.setTaskList(TaskList.builder().id(id).build());

        final Task storedEntity = taskRepository.save(entity);
        final EntityResult result = EntityResult.builder().id(storedEntity.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping
    public ResponseEntity<EntityResult> listsPost(@RequestBody final TaskListModel taskList) {
        TaskList entity = toEntity(taskList, TaskList.class);

        final TaskList storedEntity = taskListRepository.save(entity);
        final EntityResult result = EntityResult.builder().id(storedEntity.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
}