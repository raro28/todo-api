package mx.ekthor.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.domain.ItemEntity;
import mx.ekthor.domain.ItemDetails;
import mx.ekthor.services.CrudService;

@RestController
@RequestMapping("/items")
public class ItemController extends CrudController<ItemDetails, ItemEntity, String> {

    @Autowired
    protected ItemController(CrudService<ItemDetails, ItemEntity, String> crudService) {
        super(crudService);
    }
}