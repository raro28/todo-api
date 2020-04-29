package mx.ekthor.rest.controllers;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import mx.ekthor.rest.models.Data;
import mx.ekthor.services.CrudService;

public abstract class CrudController<D extends Serializable,E extends D,K> {

    private final CrudService<D, E, K> crudService;

    protected CrudController(CrudService<D, E, K> crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public Data<E> index(){
        return crudService.getAll();
    }

    @PostMapping
    public E store(@RequestBody D item){
        return crudService.store(item);
    }

    @GetMapping("{id}")
    public E detail(@PathVariable K id) throws ResponseStatusException {
        Optional<E> result = crudService.getById(id);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public E replace(@PathVariable K id, @RequestBody D item){
        return crudService.upsert(id, item);
    }

    @PatchMapping("{id}")
    public E update(@PathVariable K id, @RequestBody D item){
        Optional<E> result = crudService.update(id, item);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public E delete(@PathVariable K id){
        Optional<E> result = crudService.delete(id);

        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}