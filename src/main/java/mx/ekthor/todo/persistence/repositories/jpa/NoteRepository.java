package mx.ekthor.todo.persistence.repositories.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.domain.jpa.Task;

@Repository
public interface NoteRepository extends CrudRepository<Note, Integer>{
    Page<Note> findByTask(Task task, Pageable pageRequest);
}