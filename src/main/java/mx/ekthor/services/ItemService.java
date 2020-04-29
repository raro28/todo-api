package mx.ekthor.services;

import java.util.Optional;

import mx.ekthor.domain.Item;
import mx.ekthor.domain.ItemBase;
import mx.ekthor.rest.models.Data;

public interface ItemService {

	Data<Item> getAll();

	Optional<Item> getById(String id);

	Item store(ItemBase item);

	Optional<Item> delete(String id);

	Item upsert(String id, ItemBase item);

	Optional<Item> update(String id, ItemBase item);
}
