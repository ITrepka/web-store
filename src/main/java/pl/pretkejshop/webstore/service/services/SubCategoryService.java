package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Category;
import pl.pretkejshop.webstore.model.SubCategory;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.repository.SubCategoryRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateSubCategoryDto;
import pl.pretkejshop.webstore.service.dto.SubCategoryDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.SubCategoryDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private SubCategoryDtoMapper subCategoryDtoMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<SubCategoryDto> getAllSubCategorys() {
        return subCategoryRepository.findAll().stream()
                .map(subCategory -> subCategoryDtoMapper.toDto(subCategory))
                .collect(Collectors.toList());
    }

    @Transactional
    public SubCategoryDto getSubCategoryById(int id) throws NotFoundException {
        return subCategoryRepository.findById(id)
                .map(subCategory -> subCategoryDtoMapper.toDto(subCategory))
                .orElseThrow(() -> new NotFoundException("Not found subCategory with id = " + id));
    }

    @Transactional
    public SubCategoryDto addNewSubCategory(CreateUpdateSubCategoryDto createSubCategoryDto) throws NotFoundException {
        SubCategory subCategory = subCategoryDtoMapper.toModel(createSubCategoryDto);
        subCategory.setCreatedAt(OffsetDateTime.now());
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return subCategoryDtoMapper.toDto(savedSubCategory);
    }

    @Transactional
    public SubCategoryDto updateSubCategoryById(int id, CreateUpdateSubCategoryDto subCategoryToUpdate) throws NotFoundException {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found SubCategory with id = " + id));

        Integer categoryId = subCategoryToUpdate.getCategoryId();
        Category category = categoryId == null ? null : categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Not Found Category with id = " + categoryId));
        subCategory.setCategory(category);
        subCategory.setName(subCategoryToUpdate.getName());
        subCategory.setUpdatedAt(OffsetDateTime.now());
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return subCategoryDtoMapper.toDto(savedSubCategory);
    }

    @Transactional
    public SubCategoryDto deleteSubCategoryById(int id) throws NotFoundException {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found subCategory with id = " + id));
        subCategoryRepository.deleteById(id);
        return subCategoryDtoMapper.toDto(subCategory);
    }
}
