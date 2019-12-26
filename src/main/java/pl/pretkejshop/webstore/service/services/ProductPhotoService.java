package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Photo;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.PhotoRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.PhotoDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PhotoDtoMapper;
import pl.pretkejshop.webstore.service.mapper.ProductDtoMappper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPhotoService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private ProductDtoMappper productDtoMappper;
    @Autowired
    private PhotoDtoMapper photoDtoMapper;

    public List<PhotoDto> getProductPhotos(Integer productId) throws NotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"))
                .getPhotos().stream()
                .map(photo -> photoDtoMapper.toDto(photo))
                .collect(Collectors.toList());
    }

    public ProductDto addPhotoToProduct(Integer productId, Integer photoId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"));
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo with id = " + photoId + "not found"));

        photo.getProducts().add(product);
        product.getPhotos().add(photo);

        Product savedProduct = productRepository.save(product);
        return productDtoMappper.toDto(savedProduct);
    }

    public ProductDto deletePhotoFromProduct(Integer productId, Integer photoId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"));
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("Photo with id = " + photoId + "not found"));

        photo.getProducts().remove(product);
        product.getPhotos().remove(photo);

        Product changedProduct = productRepository.save(product);
        return productDtoMappper.toDto(changedProduct);
    }
}
