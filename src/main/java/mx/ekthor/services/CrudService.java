package mx.ekthor.services;

import java.io.Serializable;
import java.util.Optional;

import mx.ekthor.rest.models.Data;

public interface CrudService<D extends Serializable,E extends D,K> {

	Data<E> getAll();

	Optional<E> getById(K id);

	E store(D item);

	Optional<E> delete(K id);

	E upsert(K id, D item);

	Optional<E> update(K id, D item);
}
