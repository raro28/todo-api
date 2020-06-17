package mx.ekthor.todo.rest.controllers.domain.impl;

import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntity;
import static mx.ekthor.todo.rest.controllers.utils.Converters.toEntityModel;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.domain.jpa.TaskList;
import mx.ekthor.todo.persistence.repositories.jpa.TaskListRepository;
import mx.ekthor.todo.persistence.repositories.jpa.TaskRepository;
import mx.ekthor.todo.rest.controllers.domain.TaskListApi;
import mx.ekthor.todo.rest.models.TaskEntityModel;
import mx.ekthor.todo.rest.models.TaskListEntityModel;
import mx.ekthor.todo.rest.models.TaskListModel;
import mx.ekthor.todo.rest.models.TaskModel;
import mx.ekthor.todo.rest.models.responses.DataResult;
import mx.ekthor.todo.rest.models.responses.EntityResult;

@RestController
public class TaskListController implements TaskListApi{

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskListController(final TaskListRepository repository, TaskRepository taskRepository) {
        this.taskListRepository = repository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponseEntity<DataResult<TaskListEntityModel>> listsGet(final int page, final int size) {
        final DataResult<TaskListEntityModel> result = DataResult.<TaskListEntityModel>builder()
                .data(StreamSupport
                    .stream(taskListRepository.findAll().spliterator(), false)
                        .map(t -> toEntityModel(t))
                    .collect(Collectors.toList()))
                .build();
        result.setTotal(result.getData().size());

        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Void> listsIdDelete(final int id) {
        taskListRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskListModel> listsIdGet(final int id) {
        return ResponseEntity.ok().body(toEntityModel(taskListRepository.findById(id).get()));
    }

    @Override
    public ResponseEntity<Void> listsIdPut(final int id, final TaskListModel taskList) {
        final TaskList entity = taskListRepository.findById(id).get();
        entity.setTitle(taskList.getTitle());

        taskListRepository.save(entity);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<DataResult<TaskEntityModel>> listsIdTasksGet(final int id, final int page,final int size) {
        if(!taskListRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

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

    @Override
    public ResponseEntity<EntityResult> listsIdTasksPost(final int id, final TaskModel task) {
        if(!taskListRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

        Task entity = toEntity(task, Task.class);
        entity.setTaskList(TaskList.builder().id(id).build());

        final Task storedEntity = taskRepository.save(entity);
        final EntityResult result = EntityResult.builder().id(storedEntity.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<EntityResult> listsPost(final TaskListModel taskList) {
        TaskList entity = toEntity(taskList, TaskList.class);

        final TaskList storedEntity = taskListRepository.save(entity);
        final EntityResult result = EntityResult.builder().id(storedEntity.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    
}