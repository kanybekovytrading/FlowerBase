package FlowerBase.mapper;

import FlowerBase.dto.BouquetResponse;
import FlowerBase.dto.CategoryResponse;
import FlowerBase.entity.Bouquet;
import FlowerBase.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class FloraMapper {

    public CategoryResponse toDto(Category category) {
        if (category == null) return null;
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt()
        );
    }

    public BouquetResponse toDto(Bouquet bouquet) {
        if (bouquet == null) return null;
        return new BouquetResponse(
                bouquet.getId(),
                bouquet.getName(),
                bouquet.getDescription(),
                bouquet.getPrice(),
                bouquet.getSize(),
                bouquet.getBadge(),
                bouquet.getImageUrl(),
                bouquet.isInStock(),
                toDto(bouquet.getCategory()),
                bouquet.getFlowers(),
                bouquet.getCreatedAt(),
                bouquet.getUpdatedAt()
        );
    }
}
