package mx.ekthor.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;

@RestController
public class ItemController {

    @GetMapping("/items")
    public Data<Item> index(){
        final Data<Item> items = Data.<Item>builder().build();

        items.getData().add(Item.builder().title("title").description("description").build());

        return items;
    }
}