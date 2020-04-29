package mx.ekthor.rest.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.services.ItemService;

@RestController
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public Data<Item> index(){
        return itemService.getAll();
    }

    @GetMapping("/items/{id}")
    public Item detail(@PathVariable String id){
        return itemService.getById(id);
    }
}