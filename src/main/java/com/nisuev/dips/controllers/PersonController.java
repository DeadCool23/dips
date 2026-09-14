package com.nisuev.dips.controllers;

import com.nisuev.dips.controllers.requests.PersonPatchRequest;
import com.nisuev.dips.controllers.requests.PersonRequest;
import com.nisuev.dips.controllers.responses.ErrorResponse;
import com.nisuev.dips.controllers.responses.PersonResponse;
import com.nisuev.dips.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
@Tag(name = "Persons", description = "Операции над сущностью Person")
public class PersonController {

    private final PersonService personService;

    @Operation(summary = "Получить человека по id", description = "Возвращает информацию о человеке по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Человек найден",
                    content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Человек не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{personId}")
    public ResponseEntity<PersonResponse> getPerson(
            @Parameter(description = "Идентификатор человека", example = "1")
            @PathVariable Long personId
    ) {
        return ResponseEntity.ok(
                PersonResponse.fromPerson(
                        personService.getPerson(personId)
                )
        );
    }

    @Operation(summary = "Получить всех людей", description = "Возвращает список всех людей")
    @ApiResponse(responseCode = "200", description = "Список людей",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class))))
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAllPersons() {
        return ResponseEntity.ok(
                personService.getAllPersons()
                        .stream()
                        .map(PersonResponse::fromPerson)
                        .toList()
        );
    }

    @Operation(summary = "Создать человека", description = "Создаёт нового человека и возвращает заголовок Location")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Человек создан",
                    headers = @Header(name = "Location", description = "URL созданного ресурса",
                            schema = @Schema(type = "string", example = "/api/v1/persons/1")),
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<Void> createPerson(@Valid @RequestBody PersonRequest request) {
        Long id = personService.createPerson(request.toPerson());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Обновить человека", description = "Обновляет данные существующего человека")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Человек обновлён",
                    content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "Человек не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{personId}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonPatchRequest request
    ) {
        return ResponseEntity.ok(
                PersonResponse.fromPerson(
                        personService.updatePerson(personId, request.toPerson())
                )
        );
    }

    @Operation(summary = "Удалить человека", description = "Удаляет человека по идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Человек удалён", content = @Content),
            @ApiResponse(responseCode = "404", description = "Человек не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{personId}")
    public ResponseEntity<Void> deletePerson(
            @Parameter(description = "Идентификатор человека", example = "1")
            @PathVariable Long personId
    ) {
        personService.deletePerson(personId);
        return ResponseEntity.noContent().build();
    }
}