package com.nisuev.dips.controllers.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(name = "ErrorResponse", description = "Информация об ошибке")
public class ErrorResponse {
    @Schema(description = "Сообщение об ошибке", example = "Some error")
    String msg;
}
