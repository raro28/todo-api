package mx.ekthor.todo.rest.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class TaskModel {
    private String title;
    private boolean isCompleted;
}