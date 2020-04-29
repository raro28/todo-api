package mx.ekthor.services;

import mx.ekthor.rest.models.Data;
import mx.ekthor.rest.models.Item;

public interface ItemService {

	Data<Item> getAll();

	Item getById(String id);

}
