package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.CategoryNotFoundException;
import pl.pretkejshop.webstore.service.exception.ProductInvalidDataException;
import pl.pretkejshop.webstore.service.exception.ProductNotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable int id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDto addNewProduct(@RequestBody CreateUpdateProductDto createProductDto) throws ProductInvalidDataException, CategoryNotFoundException {
        return productService.addNewProduct(createProductDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable int id, @RequestBody CreateUpdateProductDto productToUpdate) throws ProductNotFoundException, ProductInvalidDataException, CategoryNotFoundException {
        return productService.updateProduct(id, productToUpdate);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable int id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }
}
