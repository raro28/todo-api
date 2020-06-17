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
import mx.ekthor.todo.rest.models.TaskEntityModel;
import mx.ekthor.todo.rest.models.TaskListEntityModel;
import mx.ekthor.todo.rest.models.TaskListModel;
import mx.ekthor.todo.rest.models.TaskModel;
import mx.ekthor.todo.rest.models.responses.DataResult;
import mx.ekthor.todo.rest.models.responses.EntityResult;

@Validated
@RequestMapping("/lists")
public interface TaskListApi {
    @Operation(summary = "Gets a paginated list of task lists", tags = {"lists", "crud"})
    @ApiResponse(responseCode = "200", description = "A list of task lists", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DataResult.class))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DataResult<TaskListEntityModel>> listsGet(@RequestParam @Min(0) @Max(20000) final int page, @RequestParam @Min(0) @Max(50) final int size);

    @Operation(summary = "Deletes a task list by its id", tags = {"lists", "crud"})
    @ApiResponse(responseCode = "204", description = "Task list deleted")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> listsIdDelete(@PathVariable @Min(0) @Max(1000000) final int id);

    @Operation(summary = "Gets a task list by its id", tags = {"lists", "crud"})
    @ApiResponse(responseCode = "200", description = "A task list", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskListModel.class))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TaskListModel> listsIdGet(@PathVariable @Min(0) @Max(1000000) final int id);

    @Operation(summary = "Replaces an existing task list", tags = {"lists", "crud"})
    @ApiResponse(responseCode = "204", description = "Task list replaced")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> listsIdPut(@PathVariable @Min(0) @Max(1000000) final int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The task list info to replace") @RequestBody final TaskListModel taskList);

    @Operation(summary = "Gets a paginated list of tasks related to an existing task list", tags = {"lists", "tasks", "crud"})
    @ApiResponse(responseCode = "200", description = "A list of tasks", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DataResult.class)))
    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DataResult<TaskEntityModel>> listsIdTasksGet(@PathVariable @Min(0) @Max(1000000) final int id, @RequestParam @Min(0) @Max(20000) final int page, @RequestParam @Min(0) @Max(50) final int size);

    @Operation(summary = "Creates a new task on an existing task list", tags = {"lists", "tasks", "crud"})
    @ApiResponse(responseCode = "201", description = "The id of the new Task", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EntityResult.class))})
    @PostMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityResult> listsIdTasksPost(@PathVariable @Min(0) @Max(1000000) final int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The task info to store") @RequestBody final TaskModel task);

    @Operation(summary = "Creates a new task list", tags = {"lists", "crud"})
    @ApiResponse(responseCode = "201", description = "The id of the new task list", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EntityResult.class))})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityResult> listsPost(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The task list info to store") @RequestBody final TaskListModel taskList);
}