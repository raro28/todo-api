package mx.ekthor.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import mx.ekthor.domain.Item;
import mx.ekthor.domain.ItemBase;
import mx.ekthor.repositories.ItemRepository;

@Component
public class DefaultItemRepository implements ItemRepository {
    private static final List<Item> ITEMS = new ArrayList<>();

    @Override
    public List<Item> getAll() {
        return ITEMS;
    }

    @Override
    public Optional<Item> getById(String id) {
        return ITEMS.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<Item> delete(String id) {
        Optional<Item> result = getById(id);

        result.ifPresent(item -> ITEMS.remove(item));

        return result;
    }

    @Override
    public Optional<Item> update(String id, ItemBase updatedItem) {
        Optional<Item> result = getById(id);

        if(result.isPresent()){
            Item item = result.get();
            
            item.setDescription(updatedItem.getDescription());
            item.setTitle(updatedItem.getTitle());
        }

        return result;
    }

    @Override
    public Item insert(String id, ItemBase item) {
        Item result = Item
            .builder()
                .id(id)
                .title(item.getTitle())
                .description(item.getDescription())
            .build();

        ITEMS.add(result);

        return result;
    }

    @Override
    public Item upsert(String id, ItemBase item) {
        delete(id);
        return insert(id, item);
    }
}