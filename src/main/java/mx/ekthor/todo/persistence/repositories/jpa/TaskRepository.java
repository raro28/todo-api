package mx.ekthor.todo.persistence.repositories.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.domain.jpa.TaskList;

public interface TaskRepository extends CrudRepository<Task, Integer>{
    Page<Task> findByTaskList(@Param("taskList") TaskList taskList, Pageable pageRequest);
}