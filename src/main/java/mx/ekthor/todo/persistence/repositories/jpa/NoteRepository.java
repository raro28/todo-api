package mx.ekthor.todo.persistence.repositories.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.domain.jpa.Task;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer>{
    List<Note> findByTask(Task task);
}