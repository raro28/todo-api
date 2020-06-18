package mx.ekthor.todo.rest.controllers.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.ekthor.todo.persistence.domain.jpa.Note;
import mx.ekthor.todo.persistence.domain.jpa.Task;
import mx.ekthor.todo.persistence.domain.jpa.TaskList;
import mx.ekthor.todo.rest.models.NoteEntityModel;
import mx.ekthor.todo.rest.models.TaskEntityModel;
import mx.ekthor.todo.rest.models.TaskListEntityModel;

public class Converters {
    private Converters(){}

    private static final ObjectMapper OM;
    static {
        OM = new ObjectMapper();
        OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static TaskListEntityModel toEntityModel(TaskList entity){
        return TaskListEntityModel
            .builder()
                .id(entity.getId())
                .title(entity.getTitle())
            .build();
    }

    public static TaskEntityModel toEntityModel(Task entity){
        return TaskEntityModel
            .builder()
                .id(entity.getId())
                .completed(entity.isCompleted())
                .title(entity.getTitle())
            .build();
    }

    public static NoteEntityModel toEntityModel(Note entity){
        return NoteEntityModel
            .builder()
                .id(entity.getId())
                .content(entity.getContent())
            .build();
    }

    public static <E,M> E toEntity(M model, Class<E> _class){
        return OM.convertValue(model, _class);
    }
}