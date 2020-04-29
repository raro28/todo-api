package mx.ekthor.todo.repositories.jpa.impl;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import mx.ekthor.todo.domain.jpa.ItemDetails;
import mx.ekthor.todo.domain.jpa.ItemEntity;
import mx.ekthor.todo.repositories.jpa.BaseRepository;

@Component
public class DefaultItemRepository extends BaseRepository<ItemDetails, ItemEntity, String> {

    @Autowired
    public DefaultItemRepository(CrudRepository<ItemEntity, String> jpaRepository, Supplier<String> keySupplier,
            BiFunction<ItemDetails, String, ItemEntity> entityMapper) {
        super(jpaRepository, keySupplier, entityMapper);
    }

}