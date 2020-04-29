package mx.ekthor.todo.rest.configuration;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.ekthor.todo.domain.jpa.ItemDetails;
import mx.ekthor.todo.domain.jpa.ItemEntity;

@Configuration
public class DomainConfiguration {

    @Bean
    public BiFunction<ItemDetails,String,ItemEntity> itemMapper(){
        return (details, id) -> ItemEntity
            .builder()
                .id(id)
                .title(details.getTitle())
                .content(details.getContent())
            .build();
    }

    @Bean
    public Supplier<String> stringKeySupplier(){
        return () -> UUID.randomUUID().toString();
    }
}