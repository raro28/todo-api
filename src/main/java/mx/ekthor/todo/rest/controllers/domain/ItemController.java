package mx.ekthor.todo.rest.controllers.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.todo.domain.jpa.ItemDetails;
import mx.ekthor.todo.domain.jpa.ItemEntity;
import mx.ekthor.todo.rest.controllers.CrudController;
import mx.ekthor.todo.services.CrudService;

@RestController
@RequestMapping("/items")
public class ItemController extends CrudController<ItemDetails, ItemEntity, String> {

    @Autowired
    protected ItemController(CrudService<ItemDetails, ItemEntity, String> crudService) {
        super(crudService);
    }
}