package mx.ekthor.services.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import mx.ekthor.domain.Item;
import mx.ekthor.domain.ItemBase;
import mx.ekthor.repositories.ItemRepository;
import mx.ekthor.rest.models.Data;
import mx.ekthor.services.ItemService;

@Component
public class DefaultItemService implements ItemService {

    private final ItemRepository itemRepository;

    public DefaultItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Data<Item> getAll() {
        return Data.<Item>builder().data(itemRepository.getAll()).build();
    }

    @Override
    public Optional<Item> getById(String id) {
        return itemRepository.getById(id);
    }

    @Override
    public Item store(ItemBase item) {
        return itemRepository.insert(UUID.randomUUID().toString(), item);
    }

    @Override
    public Optional<Item> delete(String id) {
        return itemRepository.delete(id);
    }

    @Override
    public Item upsert(String id, ItemBase item) {
        return itemRepository.upsert(id, item);
    }

    @Override
    public Optional<Item> update(String id, ItemBase updatedItem) {
        return itemRepository.update(id, updatedItem);
    }
}