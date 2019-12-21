package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pretkejshop.webstore.model.Category;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.CategoryNotFoundException;
import pl.pretkejshop.webstore.service.exception.ProductInvalidDataException;
import pl.pretkejshop.webstore.service.exception.ProductNotFoundException;
import pl.pretkejshop.webstore.service.mapper.ProductDtoMappper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDtoMappper productDtoMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> productDtoMapper.toDto(product))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto getProductById(int id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .map(product -> productDtoMapper.toDto(product))
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public ProductDto addNewProduct(CreateUpdateProductDto createProductDto) throws ProductInvalidDataException, CategoryNotFoundException {
        validCreateUpdateProduct(createProductDto);
        Product product = productDtoMapper.toModel(createProductDto);
        product.setCreatedAt(OffsetDateTime.now());
        Product savedProduct = productRepository.save(product);
        return productDtoMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(int id, CreateUpdateProductDto updateProductDto) throws ProductNotFoundException, ProductInvalidDataException, CategoryNotFoundException {
        validCreateUpdateProduct(updateProductDto);
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        product.setName(updateProductDto.getName());
        Category category = updateProductDto.getCategoryId() == null ? null :
                categoryRepository.findById(updateProductDto.getCategoryId())
                        .orElseThrow(CategoryNotFoundException::new);
        product.setCategory(category);
        product.setDescription(updateProductDto.getDescription());
        product.setTargetGender(updateProductDto.getTargetGender());
        product.setUpdatedAt(OffsetDateTime.now());
        Product savedProduct = productRepository.save(product);
        return productDtoMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto deleteProduct(int id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
        return productDtoMapper.toDto(product);
    }

    private void validCreateUpdateProduct(CreateUpdateProductDto createProductDto) throws ProductInvalidDataException {
        if (createProductDto.getName() == null || createProductDto.getDescription() == null || createProductDto.getSellingPrice() == null ||
                createProductDto.getDescription().length() < 5 || createProductDto.getSellingPrice().doubleValue() < 0) {
            throw new ProductInvalidDataException();
        }
    }
}
