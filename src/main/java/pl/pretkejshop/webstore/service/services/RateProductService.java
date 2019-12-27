package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.Rate;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.repository.RateRepository;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.ProductDtoMappper;
import pl.pretkejshop.webstore.service.mapper.RateDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private ProductDtoMappper productDtoMappper;
    @Autowired
    private RateDtoMapper rateDtoMapper;

    public List<RateDto> getProductPhotos(Integer productId) throws NotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"))
                .getRates().stream()
                .map(rate -> rateDtoMapper.toDto(rate))
                .collect(Collectors.toList());
    }

    public ProductDto addPhotoToProduct(Integer productId, Integer rateId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"));
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new NotFoundException("Rate with id = " + rateId + "not found"));

        rate.getProducts().add(product);
        product.getRates().add(rate);

        Product savedProduct = productRepository.save(product);
        return productDtoMappper.toDto(savedProduct);
    }

    public ProductDto deletePhotoFromProduct(Integer productId, Integer rateId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"));
        Rate rate = rateRepository.findById(rateId)
                .orElseThrow(() -> new NotFoundException("Rate with id = " + rateId + "not found"));

        rate.getProducts().remove(product);
        product.getRates().remove(rate);

        Product changedProduct = productRepository.save(product);
        return productDtoMappper.toDto(changedProduct);
    }
}
