package com.nisuev.dips.controllers.responses;

import com.nisuev.dips.services.models.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(name = "PersonResponse", description = "Информация о человеке")
public class PersonResponse {
    @Schema(description = "Уникальный идентификатор", example = "1")
    long id;

    @Schema(description = "Имя человека", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    String name;

    @Schema(description = "Возраст", example = "30", minimum = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    int age;

    @Schema(description = "Адрес проживания", example = "Москва")
    String address;

    @Schema(description = "Место работы", example = "Завод")
    String work;

    public static PersonResponse fromPerson(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getAge(),
                person.getAddress(),
                person.getWork()
        );
    }
}
