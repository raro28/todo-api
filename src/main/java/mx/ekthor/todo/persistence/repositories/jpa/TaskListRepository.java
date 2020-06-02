package mx.ekthor.todo.persistence.repositories.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.ekthor.todo.persistence.domain.jpa.TaskList;

@Repository
public interface TaskListRepository extends CrudRepository<TaskList, Integer> {

}