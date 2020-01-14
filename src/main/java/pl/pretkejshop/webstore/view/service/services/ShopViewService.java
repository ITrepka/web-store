package pl.pretkejshop.webstore.view.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.dto.TagDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.mapper.ProductViewDtoMapper;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<ProductViewDto> sort(String orderBy, List<ProductViewDto> products) {
        List<ProductViewDto> productsAfterSort = new ArrayList<>(products);
        switch (orderBy) {
            case "rating":
                return productsAfterSort.stream()
                        .sorted(Comparator.comparingDouble(ProductViewDto::getAverageRate))
                        .collect(Collectors.toList());
            case "date":
                return productsAfterSort.stream()
                        .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                        .collect(Collectors.toList());
            case "price":
                return productsAfterSort.stream()
                        .sorted(Comparator.comparing(ProductViewDto::getSellingPrize))
                        .collect(Collectors.toList());
            case "price-desc":
                return productsAfterSort.stream()
                        .sorted((p1, p2) -> p2.getSellingPrize().compareTo(p1.getSellingPrize()))
                        .collect(Collectors.toList());
            default:
                return productsAfterSort;
        }
    }

    public List<ProductViewDto> searchProductByText(String s, List<ProductViewDto> products) {
        List<ProductViewDto> matchedProducts = new ArrayList<>();

        List<ProductViewDto> matchedByProductName = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(s.toLowerCase())).collect(Collectors.toList());
        List<ProductViewDto> matchedByBrandName = products.stream()
                .filter(p -> p.getBrand() != null)
                .filter(p -> p.getBrand().getName().toLowerCase().contains(s.toLowerCase()))
                .collect(Collectors.toList());
        List<ProductViewDto> matchedByDescription = products.stream()
                .filter(p -> p.getDescription().toLowerCase().contains(s.toLowerCase()))
                .collect(Collectors.toList());
        List<ProductViewDto> matchedBySubCategory = products.stream()
                .filter(p -> p.getSubCategoryDto() != null)
                .filter(p -> p.getSubCategoryDto().getName().toLowerCase().contains(s.toLowerCase()))
                .collect(Collectors.toList());
        List<ProductViewDto> matchedByTag = products.stream()
                .filter(p -> p.getTagList() != null)
                .filter(p -> searchInTagList(s, p.getTagList()))
                .collect(Collectors.toList());


        matchedProducts.addAll(matchedByProductName);
        matchedProducts.addAll(matchedByBrandName);
        matchedProducts.addAll(matchedByDescription);
        matchedProducts.addAll(matchedBySubCategory);
        matchedProducts.addAll(matchedByTag);

        return matchedProducts.stream().distinct().collect(Collectors.toList());
    }

    private boolean searchInTagList(String s, List<TagDto> tagList) {
        return tagList.stream().anyMatch(tag -> tag.getName().toLowerCase().contains(s.toLowerCase()));
    }

    public List<ProductViewDto> filterBy(Integer minPrice, Integer maxPrice, List<ProductViewDto> products) {
        List<ProductViewDto> filteredProducts = new ArrayList<>(products);
        return filteredProducts.stream()
                .filter(p -> p.getSellingPrize().doubleValue() >= minPrice && p.getSellingPrize().doubleValue() <= maxPrice)
                .collect(Collectors.toList());
    }
}
