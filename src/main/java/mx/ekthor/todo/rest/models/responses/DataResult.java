package mx.ekthor.todo.rest.models.responses;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataResult<E> {
    private long total;
    private int pages;

    @Builder.Default 
    private List<E> data = new ArrayList<>();
}