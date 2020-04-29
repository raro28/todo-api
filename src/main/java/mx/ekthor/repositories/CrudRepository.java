package mx.ekthor.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<D extends Serializable,E extends D,K> {

	List<E> getAll();

    Optional<E> getById(K id);

    Optional<E> delete(K id);

    E insert(D item);

    Optional<E> update(K id, D updatedItem);

	E upsert(K id, D item);
}
