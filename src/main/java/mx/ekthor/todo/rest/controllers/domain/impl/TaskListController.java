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
public class TaskListController implements TaskListApi {

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskListController(final TaskListRepository repository, TaskRepository taskRepository) {
        this.taskListRepository = repository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponseEntity<DataResult<TaskListEntityModel>> listsGet(final int page, final int size) {
        Page<TaskList> pageResult = taskListRepository.findAll(PageRequest.of(page - 1, size));

        final DataResult<TaskListEntityModel> result = DataResult
            .<TaskListEntityModel>builder()
                .current(page)
                .size(size)
                .data(pageResult
                    .stream()
                        .map(t -> toEntityModel(t))
                    .collect(Collectors.toList()))
                .total(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
            .build();

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
    public ResponseEntity<DataResult<TaskEntityModel>> listsIdTasksGet(final int id, final int page, final int size) {
        if(!taskListRepository.existsById(id)){
            throw new NoSuchElementException(id + "");
        }

        final TaskList taskList = TaskList.builder().id(id).build();
        Page<Task> pageResult = taskRepository.findByTaskList(taskList, PageRequest.of(page - 1, size));

        final DataResult<TaskEntityModel> result = DataResult
            .<TaskEntityModel>builder()
                .current(page)
                .size(size)
                .data(pageResult
                    .stream()
                        .map(t -> toEntityModel(t))
                    .collect(Collectors.toList()))
                .total(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
            .build();

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