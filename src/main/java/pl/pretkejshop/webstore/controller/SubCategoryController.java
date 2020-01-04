package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateSubCategoryDto;
import pl.pretkejshop.webstore.service.dto.SubCategoryDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sub-categories")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public List<SubCategoryDto> getAllSubCategorys() {
        return subCategoryService.getAllSubCategorys();
    }

    @GetMapping("/{id}")
    public SubCategoryDto getSubCategoryById(@PathVariable int id) throws NotFoundException {
        return subCategoryService.getSubCategoryById(id);
    }

    @PostMapping
    public SubCategoryDto addNewSubCategory(@RequestBody CreateUpdateSubCategoryDto createSubCategoryDto) throws AlreadyExistsException, InvalidDataException, NotFoundException {
        return subCategoryService.addNewSubCategory(createSubCategoryDto);
    }

    @PutMapping("/{id}")
    public SubCategoryDto updateSubCategoryById(@PathVariable int id, @RequestBody CreateUpdateSubCategoryDto subCategoryToUpdate) throws NotFoundException, InvalidDataException {
        return subCategoryService.updateSubCategoryById(id, subCategoryToUpdate);
    }

    @DeleteMapping("/{id}")
    public SubCategoryDto deleteSubCategoryById(@PathVariable int id) throws NotFoundException {
        return subCategoryService.deleteSubCategoryById(id);
    }
}
