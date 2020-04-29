package mx.ekthor.rest.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.rest.models.ItemBase;
import mx.ekthor.services.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping
    public Data<Item> index(){
        return itemService.getAll();
    }

    @PostMapping
    public Item store(@RequestBody ItemBase item){
        return itemService.store(item);
    }

    @GetMapping("{id}")
    public Item detail(@PathVariable String id) throws ResponseStatusException {
        Optional<Item> result = itemService.getById(id);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public Item replace(@PathVariable String id, @RequestBody ItemBase item){
        return itemService.upsert(id, item);
    }

    @PatchMapping("{id}")
    public Item update(@PathVariable String id, @RequestBody ItemBase item){
        Optional<Item> result = itemService.update(id, item);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public Item delete(@PathVariable String id){
        Optional<Item> result = itemService.delete(id);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}