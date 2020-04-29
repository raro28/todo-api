package mx.ekthor.todo.repositories.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.ekthor.todo.domain.jpa.ItemEntity;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, String> {

}