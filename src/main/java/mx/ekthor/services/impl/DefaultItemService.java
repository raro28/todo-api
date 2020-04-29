package mx.ekthor.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.rest.models.ItemBase;
import mx.ekthor.services.ItemService;

@Component
public class DefaultItemService implements ItemService {

    private static final Data<Item> ITEMS = Data.<Item>builder().build();

    @Override
    public Data<Item> getAll() {
        return ITEMS;
    }

    @Override
    public Optional<Item> getById(String id) {
        return ITEMS.getData().stream()
            .filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public Item store(ItemBase item) {
        return insert(UUID.randomUUID().toString(), item);
    }

    @Override
    public Optional<Item> delete(String id) {
        Optional<Item> result = getById(id);

        result.ifPresent(item -> ITEMS.getData().remove(item));

        return result;
    }

    @Override
    public Item upsert(String id, ItemBase item) {
        delete(id);

        return insert(id, item);
    }

    private Item insert(String id, ItemBase item){
        Item result = Item
            .builder()
                .id(id)
                .title(item.getTitle())
                .description(item.getDescription())
            .build();

        ITEMS.getData().add(result);

        return result;
    }

}