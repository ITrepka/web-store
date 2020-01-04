package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;

import java.util.List;
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable int id) throws NotFoundException {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDto addNewProduct(@RequestBody CreateUpdateProductDto createProductDto) throws InvalidDataException, NotFoundException {
        return productService.addNewProduct(createProductDto);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable int id, @RequestBody CreateUpdateProductDto productToUpdate) throws NotFoundException, InvalidDataException {
        return productService.updateProduct(id, productToUpdate);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable int id) throws NotFoundException {
        return productService.deleteProduct(id);
    }
}
