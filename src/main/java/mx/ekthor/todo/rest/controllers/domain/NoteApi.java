package mx.ekthor.todo.rest.controllers.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import mx.ekthor.todo.rest.models.NoteModel;

@Validated
@RequestMapping("/notes")
public interface NoteApi {
    @Operation(summary = "Deletes a note by its id", tags = {"notes", "crud"})
    @ApiResponse(responseCode = "204", description = "Note deleted")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> notesIdDelete(@PathVariable @Min(0) @Max(1000000) final int id);

    @Operation(summary = "Gets a note by its id", tags = {"notes", "crud"})
    @ApiResponse(responseCode = "200", description = "A note", content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NoteModel.class))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<NoteModel> notesIdGet(@PathVariable @Min(0) @Max(1000000) final int id);

    @Operation(summary = "Replaces an existing note", tags = {"notes", "crud"})
    @ApiResponse(responseCode = "204", description = "Note replaced")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> notesIdPut(@PathVariable @Min(0) @Max(1000000) final int id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description="The note info to replace") @RequestBody final NoteModel note);
}