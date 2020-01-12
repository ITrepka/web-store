package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.ProductCopy;
import pl.pretkejshop.webstore.repository.ProductCopyRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateProductCopyDto;
import pl.pretkejshop.webstore.service.dto.ProductCopyDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.ProductCopyDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCopyService {
    @Autowired
    private ProductCopyRepository productCopyRepository;
    @Autowired
    private ProductCopyDtoMapper productCopyDtoMapper;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductCopyDto> getAllProductCopies() {
        return productCopyRepository.findAll().stream()
                .map(productCopy -> productCopyDtoMapper.toDto(productCopy))
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductCopyDto getProductCopyById(long id) throws NotFoundException {
        return productCopyRepository.findById(id)
                .map(productCopy -> productCopyDtoMapper.toDto(productCopy))
                .orElseThrow(() -> new NotFoundException("Not found productCopy with id = " + id));
    }

    @Transactional
    public ProductCopyDto addNewProductCopy(CreateUpdateProductCopyDto createProductCopyDto) throws NotFoundException {
        ProductCopy productCopy = productCopyDtoMapper.toModel(createProductCopyDto);
        productCopy.setCreatedAt(OffsetDateTime.now());
        ProductCopy savedProductCopy = productCopyRepository.save(productCopy);
        return productCopyDtoMapper.toDto(savedProductCopy);
    }

    @Transactional
    public ProductCopyDto updateProductCopyById(long id, CreateUpdateProductCopyDto productCopyToUpdate) throws NotFoundException {
        ProductCopy productCopy = productCopyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found ProductCopy with id = " + id));
        Integer productId = productCopyToUpdate.getProductId();
        Product product = productId == null ? null : productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Not Found product with id =" + productId));
        productCopy.setProduct(product);
        productCopy.setUpdatedAt(OffsetDateTime.now());
        ProductCopy savedProductCopy = productCopyRepository.save(productCopy);
        return productCopyDtoMapper.toDto(savedProductCopy);
    }

    @Transactional
    public ProductCopyDto deleteProductCopyById(long id) throws NotFoundException {
        ProductCopy productCopy = productCopyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found productCopy with id = " + id));
        productCopyRepository.deleteById(id);
        return productCopyDtoMapper.toDto(productCopy);
    }
}
