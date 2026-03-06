package FlowerBase.service.impl;

import FlowerBase.dto.*;
import FlowerBase.entity.*;
import FlowerBase.exception.ResourceNotFoundException;
import FlowerBase.mapper.FloraMapper;
import FlowerBase.repo.BouquetRepository;
import FlowerBase.repo.CategoryRepository;
import FlowerBase.service.BouquetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BouquetServiceImpl implements BouquetService {

    private final BouquetRepository bouquetRepo;
    private final CategoryRepository categoryRepo;
    private final FloraMapper mapper;

    // ─── READ ────────────────────────────────────────────

    @Override
    public PageResponse<BouquetResponse> search(
            Long categoryId, Boolean inStock, BouquetBadge badge,
            BigDecimal minPrice, BigDecimal maxPrice, String query, Pageable pageable) {

        var page = bouquetRepo.search(categoryId, inStock, badge, minPrice, maxPrice, query, pageable);
        return PageResponse.of(page.map(mapper::toDto));
    }

    @Override
    public BouquetResponse getById(Long id) {
        return mapper.toDto(findBouquet(id));
    }

    @Override
    public List<BouquetResponse> getLatest() {
        return bouquetRepo.findTop6ByInStockTrueOrderByCreatedAtDesc()
                .stream().map(mapper::toDto).toList();
    }

    // ─── WRITE ───────────────────────────────────────────

    @Override
    @Transactional
    public BouquetResponse create(BouquetRequest req) {
        var category = findCategory(req.categoryId());

        var bouquet = Bouquet.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .size(req.size())
                .badge(req.badge())
                .imageUrl(req.imageUrl())
                .inStock(req.inStock())
                .category(category)
                .flowers(req.flowers() != null ? req.flowers() : List.of())
                .build();

        var saved = bouquetRepo.save(bouquet);
        log.info("Created bouquet id={}, name={}", saved.getId(), saved.getName());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public BouquetResponse update(Long id, BouquetRequest req) {
        var bouquet = findBouquet(id);
        var category = findCategory(req.categoryId());

        bouquet.setName(req.name());
        bouquet.setDescription(req.description());
        bouquet.setPrice(req.price());
        bouquet.setSize(req.size());
        bouquet.setBadge(req.badge());
        bouquet.setImageUrl(req.imageUrl());
        bouquet.setInStock(req.inStock());
        bouquet.setCategory(category);
        bouquet.setFlowers(req.flowers() != null ? req.flowers() : List.of());

        log.info("Updated bouquet id={}", id);
        return mapper.toDto(bouquetRepo.save(bouquet));
    }

    @Override
    @Transactional
    public BouquetResponse patch(Long id, BouquetPatchRequest patch) {
        var bouquet = findBouquet(id);

        if (patch.name()        != null) bouquet.setName(patch.name());
        if (patch.description() != null) bouquet.setDescription(patch.description());
        if (patch.price()       != null) bouquet.setPrice(patch.price());
        if (patch.size()        != null) bouquet.setSize(patch.size());
        if (patch.badge()       != null) bouquet.setBadge(patch.badge());
        if (patch.imageUrl()    != null) bouquet.setImageUrl(patch.imageUrl());
        if (patch.inStock()     != null) bouquet.setInStock(patch.inStock());
        if (patch.flowers()     != null) bouquet.setFlowers(patch.flowers());
        if (patch.categoryId()  != null) bouquet.setCategory(findCategory(patch.categoryId()));

        log.info("Patched bouquet id={}", id);
        return mapper.toDto(bouquetRepo.save(bouquet));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!bouquetRepo.existsById(id)) throw ResourceNotFoundException.bouquet(id);
        bouquetRepo.deleteById(id);
        log.info("Deleted bouquet id={}", id);
    }

    // ─── HELPERS ─────────────────────────────────────────

    private Bouquet findBouquet(Long id) {
        return bouquetRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.bouquet(id));
    }

    private Category findCategory(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.category(id));
    }
}

