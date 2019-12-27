package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.RateProductService;

import java.util.List;

@RestController
public class RateProductController {
    @Autowired
    private RateProductService rateProductService;

    @GetMapping("/product/{productId}/rates")
    public List<RateDto> getProductPhotos(@PathVariable Integer productId) throws NotFoundException {
        return rateProductService.getProductPhotos(productId);
    }

    @PostMapping("/product/{productId}/rate/{rateId}")
    private ProductDto addRateToProduct(@PathVariable Integer productId, @PathVariable Integer rateId) throws NotFoundException {
        return rateProductService.addPhotoToProduct(productId, rateId);
    }

    @DeleteMapping("/product/{productId}/rate/{rateId}")
    private ProductDto deleteRateFromProduct(@PathVariable Integer productId, @PathVariable Integer rateId) throws NotFoundException {
        return rateProductService.deletePhotoFromProduct(productId, rateId);
    }
}
