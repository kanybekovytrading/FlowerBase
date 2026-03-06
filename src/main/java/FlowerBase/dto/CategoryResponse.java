package FlowerBase.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Категория букетов")
public record CategoryResponse(

        @Schema(description = "ID", example = "1")
        Long id,

        @Schema(description = "Название", example = "Розы")
        String name,

        @Schema(description = "Дата создания")
        LocalDateTime createdAt
) {}
