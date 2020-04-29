package mx.ekthor.todo.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<D extends Serializable,E extends D,K> {

	List<E> getAll();

    Optional<E> getById(K id);

    Optional<E> delete(K id);

    E insert(D details);

	E upsert(K id, D details);
}
