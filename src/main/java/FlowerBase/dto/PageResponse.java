package FlowerBase.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Страница результатов")
public record PageResponse<T>(

        @Schema(description = "Данные страницы")
        List<T> content,

        @Schema(description = "Текущая страница (0-based)", example = "0")
        int page,

        @Schema(description = "Размер страницы", example = "10")
        int size,

        @Schema(description = "Всего элементов", example = "42")
        long totalElements,

        @Schema(description = "Всего страниц", example = "5")
        int totalPages,

        @Schema(description = "Последняя страница", example = "false")
        boolean last
) {
    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}

