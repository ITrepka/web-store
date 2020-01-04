package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.PhotoDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductPhotoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductPhotoController {
    @Autowired
    private ProductPhotoService productPhotoService;

    @GetMapping("/products/{productId}/photos")
    public List<PhotoDto> getProductPhotos(@PathVariable Integer productId) throws NotFoundException {
        return productPhotoService.getProductPhotos(productId);
    }

    @PostMapping("/products/{productId}/photos/{photoId}")
    private ProductDto addPhotoToProduct(@PathVariable Integer productId, @PathVariable Integer photoId) throws NotFoundException {
        return productPhotoService.addPhotoToProduct(productId, photoId);
    }

    @DeleteMapping("/products/{productId}/photos/{photoId}")
    private ProductDto deletePhotoFromProduct(@PathVariable Integer productId, @PathVariable Integer photoId) throws NotFoundException {
        return productPhotoService.deletePhotoFromProduct(productId, photoId);
    }
}
