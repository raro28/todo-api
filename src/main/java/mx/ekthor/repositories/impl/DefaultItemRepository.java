package mx.ekthor.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import mx.ekthor.domain.ItemEntity;
import mx.ekthor.domain.ItemDetails;
import mx.ekthor.repositories.CrudRepository;

@Component
public class DefaultItemRepository implements CrudRepository<ItemDetails, ItemEntity, String> {
    private static final List<ItemEntity> ITEMS = new ArrayList<>();

    @Override
    public List<ItemEntity> getAll() {
        return ITEMS;
    }

    @Override
    public Optional<ItemEntity> getById(String id) {
        return ITEMS.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<ItemEntity> delete(String id) {
        Optional<ItemEntity> result = getById(id);

        result.ifPresent(item -> ITEMS.remove(item));

        return result;
    }

    @Override
    public Optional<ItemEntity> update(String id, ItemDetails details) {
        Optional<ItemEntity> result = getById(id);

        if(result.isPresent()){
            ItemEntity item = result.get();
            
            item.setDescription(details.getDescription());
            item.setTitle(details.getTitle());
        }

        return result;
    }

    @Override
    public ItemEntity insert(ItemDetails details) {
        return insert(UUID.randomUUID().toString(), details);
    }

    @Override
    public ItemEntity upsert(String id, ItemDetails details) {
        delete(id);
        return insert(id, details);
    }

    private ItemEntity insert(String id, ItemDetails details) {
        ItemEntity result = ItemEntity
            .builder()
                .id(id)
                .title(details.getTitle())
                .description(details.getDescription())
            .build();

        ITEMS.add(result);

        return result;
    }
}