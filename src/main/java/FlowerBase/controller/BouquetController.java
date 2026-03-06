package FlowerBase.controller;

import FlowerBase.dto.*;
import FlowerBase.entity.BouquetBadge;
import FlowerBase.service.BouquetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bouquets")
@RequiredArgsConstructor
@Tag(name = "Букеты", description = "CRUD операции с букетами")
public class BouquetController {

    private final BouquetService service;

    // ─── GET ALL (search + filter + pagination) ──────────

    @GetMapping
    @Operation(
            summary = "Список букетов",
            description = "Поиск, фильтрация и пагинация. Все параметры опциональны."
    )
    @ApiResponse(responseCode = "200", description = "Список получен")
    public PageResponse<BouquetResponse> list(

            @Parameter(description = "ID категории") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Только в наличии") @RequestParam(required = false) Boolean inStock,
            @Parameter(description = "Метка: NEW, HIT, SALE") @RequestParam(required = false) BouquetBadge badge,
            @Parameter(description = "Мин. цена") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Макс. цена") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Поиск по названию") @RequestParam(required = false) String q,

            @Parameter(description = "Номер страницы (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "12") int size,
            @Parameter(description = "Поле сортировки") @RequestParam(defaultValue = "createdAt") String sort,
            @Parameter(description = "Направление: asc / desc") @RequestParam(defaultValue = "desc") String dir
    ) {
        var pageable = PageRequest.of(page, size,
                dir.equalsIgnoreCase("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending());
        return service.search(categoryId, inStock, badge, minPrice, maxPrice, q, pageable);
    }

    // ─── LATEST ──────────────────────────────────────────

    @GetMapping("/latest")
    @Operation(summary = "Последние 6 букетов в наличии")
    @ApiResponse(responseCode = "200", description = "Список получен")
    public List<BouquetResponse> latest() {
        return service.getLatest();
    }

    // ─── GET ONE ─────────────────────────────────────────

    @GetMapping("/{id}")
    @Operation(summary = "Получить букет по ID")
    @ApiResponse(responseCode = "200", description = "Букет найден")
    @ApiResponse(responseCode = "404", description = "Букет не найден",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public BouquetResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // ─── CREATE ──────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать букет")
    @ApiResponse(responseCode = "201", description = "Букет создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public BouquetResponse create(@RequestBody @Valid BouquetRequest request) {
        return service.create(request);
    }

    // ─── FULL UPDATE ──────────────────────────────────────

    @PutMapping("/{id}")
    @Operation(summary = "Полное обновление букета")
    @ApiResponse(responseCode = "200", description = "Букет обновлён")
    @ApiResponse(responseCode = "404", description = "Букет не найден",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public BouquetResponse update(@PathVariable Long id,
                                  @RequestBody @Valid BouquetRequest request) {
        return service.update(id, request);
    }

    // ─── PARTIAL UPDATE ──────────────────────────────────

    @PatchMapping("/{id}")
    @Operation(summary = "Частичное обновление букета",
               description = "Обновляет только переданные поля")
    @ApiResponse(responseCode = "200", description = "Букет обновлён")
    public BouquetResponse patch(@PathVariable Long id,
                                 @RequestBody @Valid BouquetPatchRequest patch) {
        return service.patch(id, patch);
    }

    // ─── DELETE ──────────────────────────────────────────

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить букет")
    @ApiResponse(responseCode = "204", description = "Букет удалён")
    @ApiResponse(responseCode = "404", description = "Букет не найден",
            content = @Content(schema = @Schema(implementation = ApiError.class)))
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
