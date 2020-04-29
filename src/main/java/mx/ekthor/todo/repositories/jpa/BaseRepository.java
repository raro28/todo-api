package mx.ekthor.todo.repositories.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public abstract class BaseRepository<D extends Serializable,E extends D,K> implements mx.ekthor.todo.repositories.CrudRepository<D, E, K> {

    private final CrudRepository<E, K> jpaRepository;

    private final Supplier<K> keySupplier;
    private final BiFunction<D,K,E> entityMapper;

    @Autowired
    public BaseRepository(CrudRepository<E, K> jpaRepository, Supplier<K> keySupplier, BiFunction<D,K,E> entityMapper){
        this.jpaRepository = jpaRepository;
        this.keySupplier = keySupplier;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<E> getAll() {
        return StreamSupport
            .stream(jpaRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<E> getById(K id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<E> delete(K id) {
        Optional<E> result = getById(id);

        result.ifPresent(item -> jpaRepository.deleteById(id));

        return result;
    }

    @Override
    public E insert(D details) {
        return insert(keySupplier.get(), details);
    }

    @Override
    public E upsert(K id, D details) {
        return insert(id, details);
    }

    private E insert(K id, D details) {
        return jpaRepository.save(entityMapper.apply(details, id));
    }
}