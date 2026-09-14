package com.nisuev.dips.controllers.requests;

import com.nisuev.dips.services.models.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "PersonPatchRequest", description = "Данные для частичного обновления Person")
public class PersonPatchRequest {
    @Schema(description = "Имя человека", example = "Пётр")
    private String name;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    @Schema(description = "Возраст", example = "30", minimum = "0")
    private Integer age;

    @Schema(description = "Адрес проживания", example = "Москва")
    private String address;

    @Schema(description = "Место работы", example = "Завод")
    private String work;

    public Person toPerson() {
        return Person.builder()
                .name(name)
                .age(age)
                .address(address)
                .work(work)
                .build();
    }
}