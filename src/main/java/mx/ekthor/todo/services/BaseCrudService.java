package mx.ekthor.todo.services;

import java.io.Serializable;
import java.util.Optional;

import mx.ekthor.todo.repositories.CrudRepository;
import mx.ekthor.todo.rest.models.Data;

public abstract class BaseCrudService<D extends Serializable,E extends D,K> implements CrudService<D,E,K> {

    private final CrudRepository<D, E, K> itemRepository;

    public BaseCrudService(CrudRepository<D, E, K> itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Data<E> getAll() {
        return Data.<E>builder().data(itemRepository.getAll()).build();
    }

    @Override
    public Optional<E> getById(K id) {
        return itemRepository.getById(id);
    }

    @Override
    public E store(D details) {
        return itemRepository.insert(details);
    }

    @Override
    public Optional<E> delete(K id) {
        return itemRepository.delete(id);
    }

    @Override
    public E upsert(K id, D details) {
        return itemRepository.upsert(id, details);
    }
}