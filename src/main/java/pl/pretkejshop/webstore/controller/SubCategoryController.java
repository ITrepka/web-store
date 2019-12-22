package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sub_category")
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;

    @GetMapping
    public List<SubCategoryDto> getAllSubCategorys() {
        return subCategoryService.getAllSubCategorys();
    }

    @GetMapping("/{id}")
    public SubCategoryDto getSubCategoryById(@PathVariable int id) throws NotFoundException {
        return subCategoryService.getSubCategoryById(id);
    }

    @PostMapping
    public SubCategoryDto addNewSubCategory(@RequestBody CreateUpdateSubCategoryDto createSubCategoryDto) throws AlreadyExistsException, InvalidDataException {
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
