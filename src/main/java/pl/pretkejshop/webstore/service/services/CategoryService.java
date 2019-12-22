package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Category;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.service.dto.CategoryDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCategoryDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.CategoryDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryDtoMapper categoryDtoMapper;

    public List<CategoryDto> getAllCategorys() {
        return categoryRepository.findAll().stream()
                .map(category -> categoryDtoMapper.toDto(category))
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryDto getCategoryById(int id) throws NotFoundException {
        return categoryRepository.findById(id)
                .map(category -> categoryDtoMapper.toDto(category))
                .orElseThrow(() -> new NotFoundException("Not found category with id = " + id));
    }

    @Transactional
    public CategoryDto addNewCategory(CreateUpdateCategoryDto createCategoryDto) {
        Category category = categoryDtoMapper.toModel(createCategoryDto);
        category.setCreatedAt(OffsetDateTime.now());
        Category savedCategory = categoryRepository.save(category);
        return categoryDtoMapper.toDto(savedCategory);
    }

    @Transactional
    public CategoryDto updateCategoryById(int id, CreateUpdateCategoryDto categoryToUpdate) throws NotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Category with id = " + id));
        category.setName(categoryToUpdate.getName());
        category.setUpdatedAt(OffsetDateTime.now());
        Category savedCategory = categoryRepository.save(category);
        return categoryDtoMapper.toDto(savedCategory);
    }

    @Transactional
    public CategoryDto deleteCategoryById(int id) throws NotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found category with id = " + id));
        categoryRepository.deleteById(id);
        return categoryDtoMapper.toDto(category);
    }
}
