package FlowerBase.service.impl;

import FlowerBase.dto.CategoryRequest;
import FlowerBase.dto.CategoryResponse;
import FlowerBase.entity.Category;
import FlowerBase.exception.ConflictException;
import FlowerBase.exception.ResourceNotFoundException;
import FlowerBase.mapper.FloraMapper;
import FlowerBase.repo.CategoryRepository;
import FlowerBase.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;
    private final FloraMapper mapper;

    @Override
    public List<CategoryResponse> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public CategoryResponse getById(Long id) {
        return mapper.toDto(find(id));
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest req) {
        if (repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Категория '" + req.name() + "' уже существует");
        }
        var saved = repo.save(Category.builder().name(req.name()).build());
        log.info("Created category id={}, name={}", saved.getId(), saved.getName());
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest req) {
        var cat = find(id);
        if (!cat.getName().equalsIgnoreCase(req.name())
                && repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Категория '" + req.name() + "' уже существует");
        }
        cat.setName(req.name());
        return mapper.toDto(repo.save(cat));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw ResourceNotFoundException.category(id);
        repo.deleteById(id);
        log.info("Deleted category id={}", id);
    }

    private Category find(Long id) {
        return repo.findById(id).orElseThrow(() -> ResourceNotFoundException.category(id));
    }
}

