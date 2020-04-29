package mx.ekthor.services;

import java.util.Optional;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;
import mx.ekthor.rest.models.ItemBase;

public interface ItemService {

	Data<Item> getAll();

	Optional<Item> getById(String id);

	Item store(ItemBase item);
}
