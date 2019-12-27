package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Photo;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.Tag;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.repository.TagRepository;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.dto.TagDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.ProductDtoMappper;
import pl.pretkejshop.webstore.service.mapper.TagDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagDtoMapper tagDtoMapper;
    @Autowired
    private ProductDtoMappper productDtoMappper;

    public List<TagDto> getProductPhotos(Integer productId) throws NotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"))
                .getTagList().stream()
                .map(tag -> tagDtoMapper.toDto(tag))
                .collect(Collectors.toList());
    }

    public ProductDto addPhotoToProduct(Integer productId, Integer tagId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("Tag with id = " + tagId + "not found"));

        tag.getProducts().add(product);
        product.getTagList().add(tag);

        Product savedProduct = productRepository.save(product);
        return productDtoMappper.toDto(savedProduct);
    }

    public ProductDto deletePhotoFromProduct(Integer productId, Integer tagId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id = " + productId + "not found"));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException("Tag with id = " + tagId + "not found"));

        tag.getProducts().remove(product);
        product.getTagList().remove(tag);

        Product changedProduct = productRepository.save(product);
        return productDtoMappper.toDto(changedProduct);
    }
}
