package mx.ekthor.repositories;

import java.util.List;
import java.util.Optional;

import mx.ekthor.domain.Item;
import mx.ekthor.domain.ItemBase;

public interface ItemRepository {

	List<Item> getAll();

    Optional<Item> getById(String id);

    Optional<Item> delete(String id);

    Item insert(String id, ItemBase item);

    Optional<Item> update(String id, ItemBase updatedItem);

	Item upsert(String id, ItemBase item);
}
