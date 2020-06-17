package mx.ekthor.todo.rest.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    String timestamp;
    int status;
    String error;
    String message;
    String path;
}