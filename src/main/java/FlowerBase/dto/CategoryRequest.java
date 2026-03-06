package FlowerBase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание категории")
public record CategoryRequest(

        @NotBlank(message = "Название категории обязательно")
        @Size(max = 100)
        @Schema(description = "Название категории", example = "Розы")
        String name
) {}
