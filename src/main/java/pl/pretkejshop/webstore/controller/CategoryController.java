package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CategoryDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCategoryDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategorys() {
        return categoryService.getAllCategorys();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable int id) throws NotFoundException {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryDto addNewCategory(@RequestBody CreateUpdateCategoryDto createCategoryDto) throws AlreadyExistsException, InvalidDataException {
        return categoryService.addNewCategory(createCategoryDto);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategoryById(@PathVariable int id, @RequestBody CreateUpdateCategoryDto categoryToUpdate) throws NotFoundException, InvalidDataException {
        return categoryService.updateCategoryById(id, categoryToUpdate);
    }

    @DeleteMapping("/{id}")
    public CategoryDto deleteCategoryById(@PathVariable int id) throws NotFoundException {
        return categoryService.deleteCategoryById(id);
    }
}
