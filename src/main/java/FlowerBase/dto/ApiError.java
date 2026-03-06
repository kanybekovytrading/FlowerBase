package FlowerBase.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Ошибка API")
public record ApiError(

        @Schema(description = "HTTP статус", example = "404")
        int status,

        @Schema(description = "Сообщение об ошибке", example = "Букет не найден")
        String message,

        @Schema(description = "Путь запроса", example = "/api/v1/bouquets/99")
        String path,

        @Schema(description = "Время ошибки")
        LocalDateTime timestamp,

        @Schema(description = "Ошибки валидации полей", nullable = true)
        Map<String, String> fieldErrors
) {
    public static ApiError of(int status, String message, String path) {
        return new ApiError(status, message, path, LocalDateTime.now(), null);
    }

    public static ApiError validation(String path, Map<String, String> fieldErrors) {
        return new ApiError(400, "Ошибка валидации", path, LocalDateTime.now(), fieldErrors);
    }
}
