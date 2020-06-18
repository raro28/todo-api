package mx.ekthor.todo.rest.controllers.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mx.ekthor.todo.rest.models.NoteEntityModel;
import mx.ekthor.todo.rest.models.NoteModel;
import mx.ekthor.todo.rest.models.TaskModel;
import mx.ekthor.todo.rest.models.responses.DataResult;
import mx.ekthor.todo.rest.models.responses.EntityResult;

@Validated
@RequestMapping("/tasks")
public interface TaskApi {
    @Operation(summary = "Deletes a task by its id", tags = {"tasks", "crud"}, security = @SecurityRequirement(name = "oAuth2", scopes = "write"))
    @ApiResponse(responseCode = "204", description = "Task deleted")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> tasksIdDelete(@PathVariable @Min(0) @Max(1000000) final int id);

    @Operation(summary = "Gets a task by its id", tags = {"tasks", "crud"}, security = @SecurityRequirement(name = "oAuth2", scopes = "read"))
    @ApiResponse(responseCode = "200", description = "A task", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskModel.class))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TaskModel> tasksIdGet(@PathVariable @Min(0) @Max(1000000) final int id);

    @Operation(summary = "Gets a paginated list of notes related to an existing task", tags = {"tasks", "notes", "crud"}, security = @SecurityRequirement(name = "oAuth2", scopes = "read"))
    @ApiResponse(responseCode = "200", description = "A list of notes", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DataResult.class))})
    @GetMapping(value = "/{id}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DataResult<NoteEntityModel>> tasksIdNotesGet(@PathVariable @Min(0) @Max(1000000) final int id, @RequestParam @Min(1) @Max(20000) final int page, @RequestParam @Min(1) @Max(50) final int size);

    @Operation(summary = "Creates a new note on an existing task", tags = {"tasks", "notes", "crud"}, security = @SecurityRequirement(name = "oAuth2", scopes = "write"))
    @ApiResponse(responseCode = "201", description = "The id of the new note", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EntityResult.class))})
    @PostMapping(value = "/{id}/notes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityResult> tasksIdNotesPost(@PathVariable @Min(0) @Max(1000000) final int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description="The note info to store") @RequestBody final NoteModel note);

    @Operation(summary = "Replaces an existing task", tags = {"tasks", "crud"}, security = @SecurityRequirement(name = "oAuth2", scopes = "write"))
    @ApiResponse(responseCode = "204", description = "Task replaced")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> tasksIdPut(@PathVariable @Min(0) @Max(1000000) final int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description="The task info to replace") @RequestBody final TaskModel task);
}