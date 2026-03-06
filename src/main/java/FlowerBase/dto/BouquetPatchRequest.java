package FlowerBase.dto;

import FlowerBase.entity.BouquetBadge;
import FlowerBase.entity.BouquetSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Частичное обновление букета (только заполненные поля)")
public record BouquetPatchRequest(

        @Size(max = 255)
        @Schema(description = "Название", nullable = true)
        String name,

        @Size(max = 2000)
        @Schema(description = "Описание", nullable = true)
        String description,

        @DecimalMin(value = "0.00", inclusive = false)
        @Schema(description = "Цена", nullable = true)
        BigDecimal price,

        @Schema(description = "Размер", nullable = true)
        BouquetSize size,

        @Schema(description = "Метка", nullable = true)
        BouquetBadge badge,

        @Size(max = 1000)
        @Schema(description = "URL изображения", nullable = true)
        String imageUrl,

        @Schema(description = "В наличии", nullable = true)
        Boolean inStock,

        @Schema(description = "ID категории", nullable = true)
        Long categoryId,

        @Schema(description = "Состав цветов", nullable = true)
        List<String> flowers
) {}

