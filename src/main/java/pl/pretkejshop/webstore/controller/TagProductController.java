package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.dto.TagDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.TagProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TagProductController {
    @Autowired
    private TagProductService tagProductService;

    @GetMapping("/products/{productId}/tags")
    public List<TagDto> getProductPhotos(@PathVariable Integer productId) throws NotFoundException {
        return tagProductService.getProductPhotos(productId);
    }

    @PostMapping("/products/{productId}/tags/{tagId}")
    private ProductDto addTagToProduct(@PathVariable Integer productId, @PathVariable Integer tagId) throws NotFoundException {
        return tagProductService.addPhotoToProduct(productId, tagId);
    }

    @DeleteMapping("/products/{productId}/tags/{tagId}")
    private ProductDto deleteTagFromProduct(@PathVariable Integer productId, @PathVariable Integer tagId) throws NotFoundException {
        return tagProductService.deletePhotoFromProduct(productId, tagId);
    }
}
