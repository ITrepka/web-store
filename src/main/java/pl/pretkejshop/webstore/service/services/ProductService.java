package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pretkejshop.webstore.model.*;
import pl.pretkejshop.webstore.repository.BrandRepository;
import pl.pretkejshop.webstore.repository.CategoryRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.repository.SubCategoryRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
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
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Transactional
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> productDtoMapper.toDto(product))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto getProductById(int id) throws NotFoundException {
        return productRepository.findById(id)
                .map(product -> productDtoMapper.toDto(product))
                .orElseThrow(() -> new NotFoundException("Product with id = " + id + " not found"));
    }

    @Transactional
    public ProductDto addNewProduct(CreateUpdateProductDto createProductDto) throws InvalidDataException, NotFoundException {
        validCreateUpdateProduct(createProductDto);
        Product product = productDtoMapper.toModel(createProductDto);
        product.setCreatedAt(OffsetDateTime.now());
        Product savedProduct = productRepository.save(product);
        return productDtoMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(int id, CreateUpdateProductDto updateProductDto) throws NotFoundException, InvalidDataException {
        validCreateUpdateProduct(updateProductDto);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id = " + id + " not found"));
        product.setName(updateProductDto.getName());
        SubCategory subCategory = updateProductDto.getSubCategoryId() == null ? null :
                subCategoryRepository.findById(updateProductDto.getSubCategoryId())
                        .orElseThrow(() -> new NotFoundException("Category with id = " + updateProductDto.getSubCategoryId() + " not found"));
        Integer brandId = updateProductDto.getBrandId();
        Brand brand = brandId == null ? null : brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand with id = " + brandId + " not found"));
        product.setBrand(brand);
        product.setSubCategory(subCategory);
        product.setDescription(updateProductDto.getDescription());
        product.setTargetGender(updateProductDto.getTargetGender());
        product.setUpdatedAt(OffsetDateTime.now());
        Product savedProduct = productRepository.save(product);
        return productDtoMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto deleteProduct(int id) throws NotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id = " + id + " not found"));
        productRepository.delete(product);
        return productDtoMapper.toDto(product);
    }

    private void validCreateUpdateProduct(CreateUpdateProductDto createProductDto) throws InvalidDataException {
        if (createProductDto.getName() == null || createProductDto.getDescription() == null || createProductDto.getSellingPrice() == null ||
                createProductDto.getDescription().length() < 5 || createProductDto.getSellingPrice().doubleValue() < 0) {
            throw new InvalidDataException("Product invalid data");
        }
    }

    public List<ProductDto> getAllAvaibleProducts() {
        return productRepository.findAll().stream()
                .filter(this::isAnyCopyAvaibleToBuy)
                .map(product -> productDtoMapper.toDto(product))
                .collect(Collectors.toList());
    }

    private boolean isAnyCopyAvaibleToBuy(Product product) {
        List<ProductCopy> productCopies = product.getProductCopies();
        for (ProductCopy productCopy : productCopies) {
            if (productCopy.getOrder() == null) {
                return true;
            }
        }
        return false;
    }
}
