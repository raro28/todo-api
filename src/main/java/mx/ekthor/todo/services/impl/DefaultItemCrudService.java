package mx.ekthor.todo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.ekthor.todo.domain.jpa.ItemDetails;
import mx.ekthor.todo.domain.jpa.ItemEntity;
import mx.ekthor.todo.repositories.CrudRepository;
import mx.ekthor.todo.services.BaseCrudService;

@Component
public class DefaultItemCrudService extends BaseCrudService<ItemDetails, ItemEntity, String> {

    @Autowired
    public DefaultItemCrudService(CrudRepository<ItemDetails, ItemEntity, String> itemRepository) {
        super(itemRepository);
    }

}