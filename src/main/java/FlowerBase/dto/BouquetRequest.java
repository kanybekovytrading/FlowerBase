package FlowerBase.dto;

import FlowerBase.entity.BouquetBadge;
import FlowerBase.entity.BouquetSize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

// ─── Request ────────────────────────────────────────
@Schema(description = "Запрос на создание / обновление букета")
public record BouquetRequest(

        @NotBlank(message = "Название обязательно")
        @Size(max = 255)
        @Schema(description = "Название букета", example = "Розовая мечта")
        String name,

        @Size(max = 2000)
        @Schema(description = "Описание", example = "Нежный букет из пионовидных роз")
        String description,

        @NotNull(message = "Цена обязательна")
        @DecimalMin(value = "0.00", inclusive = false, message = "Цена должна быть больше 0")
        @Schema(description = "Цена в рублях", example = "3500.00")
        BigDecimal price,

        @NotNull(message = "Размер обязателен")
        @Schema(description = "Размер букета", example = "M")
        BouquetSize size,

        @Schema(description = "Метка", example = "HIT", nullable = true)
        BouquetBadge badge,

        @Size(max = 1000)
        @Schema(description = "URL изображения", example = "https://example.com/image.jpg", nullable = true)
        String imageUrl,

        @Schema(description = "Наличие", example = "true")
        boolean inStock,

        @NotNull(message = "Категория обязательна")
        @Schema(description = "ID категории", example = "1")
        Long categoryId,

        @Schema(description = "Список цветов в составе", example = "[\"Розы\",\"Эустома\"]")
        List<@NotBlank String> flowers
) {}
