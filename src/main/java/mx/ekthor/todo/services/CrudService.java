package mx.ekthor.todo.services;

import java.io.Serializable;
import java.util.Optional;

import mx.ekthor.todo.rest.models.Data;

public interface CrudService<D extends Serializable,E extends D,K> {

	Data<E> getAll();

	Optional<E> getById(K id);

	E store(D details);

	Optional<E> delete(K id);

	E upsert(K id, D details);
}
