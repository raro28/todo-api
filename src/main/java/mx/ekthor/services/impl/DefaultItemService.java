package mx.ekthor.services.impl;

import java.util.UUID;

import org.springframework.stereotype.Component;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.services.ItemService;

@Component
public class DefaultItemService implements ItemService {

    @Override
    public Data<Item> getAll() {
        Data<Item> result = Data.<Item>builder().build();

        result.getData().add(Item
            .builder()
                .id(UUID.randomUUID().toString())
                .title("title")
                .description("description")
            .build());

        return result;
    }

    @Override
    public Item getById(String id) {
        return Item
            .builder()
                .id(id)
                .title("title")
                .description("description")
            .build();
    }

}