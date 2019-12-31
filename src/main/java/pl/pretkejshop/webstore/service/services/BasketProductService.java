package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Basket;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.BasketRepository;
import pl.pretkejshop.webstore.repository.ProductRepository;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.BasketDtoMapper;
import pl.pretkejshop.webstore.service.mapper.ProductDtoMappper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketProductService {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDtoMappper productDtoMappper;
    @Autowired
    private BasketDtoMapper basketDtoMapper;

    public List<ProductDto> getBasketProducts(Integer basketId) throws NotFoundException {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("Not found basket with id = " + basketId));
        return basket.getProducts().stream().map(product -> productDtoMappper.toDto(product)).collect(Collectors.toList());
    }

    @Transactional
    public BasketDto addProductToBasket(Integer basketId, Integer productId) throws NotFoundException {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("Not found basket with id = " + basketId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Not found product with id = " + productId));

        product.getBaskets().add(basket);
        basket.getProducts().add(product);

        Basket savedBasket = basketRepository.save(basket);
        return basketDtoMapper.toDto(savedBasket);
    }

    @Transactional
    public BasketDto deleteProductFromBasket(Integer basketId, Integer productId) throws NotFoundException {
        Basket basket = basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("Not found basket with id = " + basketId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Not found product with id = " + productId));

        product.getBaskets().remove(basket);
        basket.getProducts().remove(product);

        return basketDtoMapper.toDto(basket);
    }
}
