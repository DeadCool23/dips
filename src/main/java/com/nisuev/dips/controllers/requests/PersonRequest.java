package com.nisuev.dips.controllers.requests;

import com.nisuev.dips.services.models.Person;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(name = "PersonRequest", description = "Данные для создания Person")
public class PersonRequest {
    @NotBlank
    @Schema(description = "Имя человека", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Min(0)
    @Schema(description = "Возраст", example = "30", minimum = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer age;

    @NotBlank
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
