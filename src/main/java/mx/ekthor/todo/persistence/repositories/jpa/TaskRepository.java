package mx.ekthor.todo.persistence.repositories.jpa;

import org.springframework.data.repository.CrudRepository;

import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.domain.jpa.TaskList;

public interface TaskRepository extends CrudRepository<Task, Integer>{
    Iterable<Task> findByTaskList(TaskList taskList);
}