package FlowerBase.dto;

import FlowerBase.entity.BouquetBadge;
import FlowerBase.entity.BouquetSize;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Данные букета")
public record BouquetResponse(

        @Schema(description = "ID", example = "1")
        Long id,

        @Schema(description = "Название", example = "Розовая мечта")
        String name,

        @Schema(description = "Описание")
        String description,

        @Schema(description = "Цена", example = "3500.00")
        BigDecimal price,

        @Schema(description = "Размер", example = "M")
        BouquetSize size,

        @Schema(description = "Метка", example = "HIT")
        BouquetBadge badge,

        @Schema(description = "URL изображения")
        String imageUrl,

        @Schema(description = "В наличии", example = "true")
        boolean inStock,

        @Schema(description = "Категория")
        CategoryResponse category,

        @Schema(description = "Состав цветов")
        List<String> flowers,

        @Schema(description = "Дата создания")
        LocalDateTime createdAt,

        @Schema(description = "Дата обновления")
        LocalDateTime updatedAt
) {}

