package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductCopyDto;
import pl.pretkejshop.webstore.service.dto.ProductCopyDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductCopyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products-copies")
public class ProductCopyController {
    @Autowired
    ProductCopyService productCopyService;

    @GetMapping
    public List<ProductCopyDto> getAllProductCopies() {
        return productCopyService.getAllProductCopies();
    }

    @GetMapping("/{id}")
    public ProductCopyDto getProductCopyById(@PathVariable long id) throws NotFoundException {
        return productCopyService.getProductCopyById(id);
    }

    @PostMapping
    public ProductCopyDto addNewProductCopy(@RequestBody CreateUpdateProductCopyDto createProductCopyDto) throws AlreadyExistsException, InvalidDataException, NotFoundException {
        return productCopyService.addNewProductCopy(createProductCopyDto);
    }

    @PutMapping("/{id}")
    public ProductCopyDto updateProductCopyById(@PathVariable long id, @RequestBody CreateUpdateProductCopyDto productCopyToUpdate) throws NotFoundException, InvalidDataException {
        return productCopyService.updateProductCopyById(id, productCopyToUpdate);
    }

    @DeleteMapping("/{id}")
    public ProductCopyDto deleteProductCopyById(@PathVariable long id) throws NotFoundException {
        return productCopyService.deleteProductCopyById(id);
    }
}
