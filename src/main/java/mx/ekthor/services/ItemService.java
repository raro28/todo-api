package mx.ekthor.services;

import java.util.Optional;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.rest.models.ItemBase;

public interface ItemService {

	Data<Item> getAll();

	Optional<Item> getById(String id);

	Item store(ItemBase item);

	Optional<Item> delete(String id);

	Item upsert(String id, ItemBase item);

	Optional<Item> update(String id, ItemBase item);
}
