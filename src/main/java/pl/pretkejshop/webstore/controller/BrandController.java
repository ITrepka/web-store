package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.BrandDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBrandDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.BrandService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    @Autowired
    BrandService brandService;

    @GetMapping
    public List<BrandDto> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public BrandDto getBrandById(@PathVariable int id) throws NotFoundException {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public BrandDto addNewBrand(@RequestBody CreateUpdateBrandDto createBrandDto) throws InvalidDataException, NotFoundException {
        return brandService.addNewBrand(createBrandDto);
    }

    @PutMapping("/{id}")
    public BrandDto updateBrandById(@PathVariable int id, @RequestBody CreateUpdateBrandDto brandToUpdate) throws NotFoundException, InvalidDataException {
        return brandService.updateBrandById(id, brandToUpdate);
    }

    @DeleteMapping("/{id}")
    public BrandDto deleteBrandById(@PathVariable int id) throws NotFoundException {
        return brandService.deleteBrandById(id);
    }
}
