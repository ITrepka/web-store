package pl.pretkejshop.webstore.view.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.mapper.ProductViewDtoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopViewService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductViewDtoMapper productViewDtoMapper;

    public List<ProductViewDto> getAllProducts() throws NotFoundException {
        List<ProductDto> allProducts = productService.getAllProducts();
        List<ProductViewDto> products = new ArrayList<>();
        for (ProductDto product : allProducts) {
            products.add(productViewDtoMapper.toDto(product));
        }
        return products;
    }

    public ProductViewDto getProductById(Integer id) throws NotFoundException {
        return productViewDtoMapper.toDto(productService.getProductById(id));
    }


    public List<ProductViewDto> getTopRatedProducts(List<ProductViewDto> products) {
        List<ProductViewDto> topRatedProducts = new ArrayList<>(products);
        return topRatedProducts.stream()
                .sorted((p1, p2) -> (int) (p2.getAverageRate() - p1.getAverageRate()))
                .collect(Collectors.toList());
    }

    public void sort(String orderBy, List<ProductViewDto> products) {
        switch (orderBy) {
//            case
        }
    }
}
