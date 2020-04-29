package mx.ekthor.services.impl;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.stereotype.Component;

import mx.ekthor.repositories.CrudRepository;
import mx.ekthor.rest.models.Data;
import mx.ekthor.services.CrudService;

@Component
public class DefaultCrudService<D extends Serializable,E extends D,K> implements CrudService<D,E,K> {

    private final CrudRepository<D, E, K> itemRepository;

    public DefaultCrudService(CrudRepository<D, E, K> itemRepository) {
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
    public E store(D item) {
        return itemRepository.insert(item);
    }

    @Override
    public Optional<E> delete(K id) {
        return itemRepository.delete(id);
    }

    @Override
    public E upsert(K id, D item) {
        return itemRepository.upsert(id, item);
    }

    @Override
    public Optional<E> update(K id, D updatedItem) {
        return itemRepository.update(id, updatedItem);
    }
}