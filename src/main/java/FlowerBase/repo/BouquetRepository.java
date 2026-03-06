package FlowerBase.repo;

import FlowerBase.entity.Bouquet;
import FlowerBase.entity.BouquetBadge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BouquetRepository extends JpaRepository<Bouquet, Long>,
        JpaSpecificationExecutor<Bouquet> {

    Page<Bouquet> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Bouquet> findByInStock(boolean inStock, Pageable pageable);

    Page<Bouquet> findByBadge(BouquetBadge badge, Pageable pageable);

    @Query("""
            SELECT b FROM Bouquet b
            WHERE (:categoryId IS NULL OR b.category.id = :categoryId)
              AND (:inStock     IS NULL OR b.inStock = :inStock)
              AND (:badge       IS NULL OR b.badge = :badge)
              AND (:minPrice    IS NULL OR b.price >= :minPrice)
              AND (:maxPrice    IS NULL OR b.price <= :maxPrice)
              AND (:search      IS NULL OR LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Bouquet> search(
            @Param("categoryId") Long categoryId,
            @Param("inStock")    Boolean inStock,
            @Param("badge")      BouquetBadge badge,
            @Param("minPrice")   BigDecimal minPrice,
            @Param("maxPrice")   BigDecimal maxPrice,
            @Param("search")     String search,
            Pageable pageable
    );

    List<Bouquet> findTop6ByInStockTrueOrderByCreatedAtDesc();

    boolean existsByNameIgnoreCase(String name);
}

