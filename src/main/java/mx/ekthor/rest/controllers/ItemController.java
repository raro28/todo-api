package mx.ekthor.rest.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.rest.models.ItemBase;
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

    @PostMapping("/items")
    public Item store(@RequestBody ItemBase item){
        return itemService.store(item);
    }

    @GetMapping("/items/{id}")
    public Item detail(@PathVariable String id) throws ResponseStatusException {
        Optional<Item> result = itemService.getById(id);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}