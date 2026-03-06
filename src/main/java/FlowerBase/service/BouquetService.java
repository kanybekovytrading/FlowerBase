package FlowerBase.service;

import FlowerBase.dto.BouquetPatchRequest;
import FlowerBase.dto.BouquetRequest;
import FlowerBase.dto.BouquetResponse;
import FlowerBase.dto.PageResponse;
import FlowerBase.entity.BouquetBadge;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface BouquetService {

    PageResponse<BouquetResponse> search(
            Long categoryId,
            Boolean inStock,
            BouquetBadge badge,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String query,
            Pageable pageable
    );

    BouquetResponse getById(Long id);

    List<BouquetResponse> getLatest();

    BouquetResponse create(BouquetRequest request);

    BouquetResponse update(Long id, BouquetRequest request);

    BouquetResponse patch(Long id, BouquetPatchRequest patch);

    void delete(Long id);
}
