package FlowerBase.controller;

import FlowerBase.dto.ApiError;
import FlowerBase.dto.CategoryRequest;
import FlowerBase.dto.CategoryResponse;
import FlowerBase.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Категории", description = "Управление категориями букетов")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    @Operation(summary = "Все категории")
    @ApiResponse(responseCode = "200", description = "Список категорий")
    public List<CategoryResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Категория по ID")
    @ApiResponse(responseCode = "200", description = "Категория найдена")
    @ApiResponse(responseCode = "404", description = "Категория не найдена",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public CategoryResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать категорию")
    @ApiResponse(responseCode = "201", description = "Категория создана")
    @ApiResponse(responseCode = "409", description = "Категория уже существует",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public CategoryResponse create(@RequestBody @Valid CategoryRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить категорию")
    @ApiResponse(responseCode = "200", description = "Категория обновлена")
    public CategoryResponse update(@PathVariable Long id,
                                   @RequestBody @Valid CategoryRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить категорию")
    @ApiResponse(responseCode = "204", description = "Категория удалена")
    @ApiResponse(responseCode = "404", description = "Категория не найдена",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
