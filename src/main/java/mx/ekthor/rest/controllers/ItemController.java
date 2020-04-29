package mx.ekthor.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.domain.Item;
import mx.ekthor.domain.ItemBase;
import mx.ekthor.services.CrudService;

@RestController
@RequestMapping("/items")
public class ItemController extends CrudController<ItemBase, Item, String> {

    @Autowired
    protected ItemController(CrudService<ItemBase, Item, String> crudService) {
        super(crudService);
    }
}