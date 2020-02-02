package pl.pretkejshop.webstore.view.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.service.dto.*;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.*;
import pl.pretkejshop.webstore.view.model.ShopPageViewModel;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.dto.UserViewDto;
import pl.pretkejshop.webstore.view.service.mapper.ProductViewDtoMapper;
import pl.pretkejshop.webstore.view.service.mapper.UserViewDtoMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopViewService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductViewDtoMapper productViewDtoMapper;
    @Autowired
    private RateProductService rateProductService;
    @Autowired
    private RateService rateService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserViewDtoMapper userViewDtoMapper;
    @Autowired
    private PersonalDataService personalDataService;

    public List<ProductViewDto> getAllAvaibleProducts() throws NotFoundException {
        List<ProductDto> allProducts = productService.getAllAvaibleProducts();
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
                        .sorted((p1, p2) -> p2.getAverageRate() - p1.getAverageRate() > 0 ? 1 : p2.getAverageRate() - p1.getAverageRate() == 0 ? 0 : -1 )
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


    public ShopPageViewModel getShopPage(Integer pageNumber, String orderBy, String s, Integer min_price, Integer max_price) throws NotFoundException {
        List<ProductViewDto> products = getAllAvaibleProducts();
        int pageNumberField = pageNumber == null ? 1 : pageNumber;
        List<ProductViewDto> productsMainView = handleRequestParams(products, orderBy, s, min_price, max_price, pageNumberField, 12);
        List<ProductViewDto> productToDisplayOnPage = productsMainView.stream().skip((pageNumberField - 1) * 12).limit(12).collect(Collectors.toList());
        return ShopPageViewModel.builder()
                .ourProductsSample(products.stream().limit(5).collect(Collectors.toList()))
                .topRatedProducts(getTopRatedProducts(products).stream().limit(5).collect(Collectors.toList()))
                .numberOfProductsOnTheSite(12)
                .pageNumber(pageNumberField)
                .amountOfPages(getAmountOfPages(productsMainView.size(), 12))
                .paragraph1(getParagraph1(pageNumberField, productsMainView.size()))
                .productsMainView(productToDisplayOnPage)
                .build();
    }

    private List<ProductViewDto> handleRequestParams(List<ProductViewDto> products, String orderBy, String s, Integer min_price, Integer max_price, int pageNumber, int numberOfProductsOnPage) {
        List<ProductViewDto> productsToView = new ArrayList<>(products);
        if (s != null) {
            productsToView = searchProductByText(s, productsToView);
        }
        if (min_price != null && max_price != null) {
            productsToView = filterBy(min_price, max_price, productsToView);
        }
        if (orderBy != null){
            productsToView = sort(orderBy, productsToView);
        }
        return productsToView;
    }

    private String getParagraph1(int pageNumberField, int numberOfProducts) {
        int numberOfLastProductOnPage = Math.min(12 * pageNumberField, numberOfProducts);
        return (1 + 12 * (pageNumberField - 1)) + "-" + numberOfLastProductOnPage + " z " + numberOfProducts + " produktów.";
    }

    private int getAmountOfPages(int numberOfProducts, int productsOnOnePage) {
        double numberOfPagesRatio = (double) numberOfProducts / productsOnOnePage;
        return numberOfPagesRatio % 1 == 0 ? (int) numberOfPagesRatio : (int) numberOfPagesRatio + 1;
    }

    public List<ProductViewDto> getRelatedProducts(ProductViewDto product) throws NotFoundException {
      return searchProductByText(product.getSubCategoryDto().getName(), getAllAvaibleProducts()).stream()
              .limit(4)
              .collect(Collectors.toList());
    }

    public void addRateToProduct(Integer rate, CreateUpdateCommentDto comment, Integer productId, String email) throws NotFoundException {
        CommentDto commentDto = commentService.addNewComment(comment);
        UserDto user = userService.getUserByEmail(email);
        getProductById(productId);
        CreateUpdateRateDto rateDto = new CreateUpdateRateDto(rate, commentDto.getId(), user.getId());
        RateDto savedRate = rateService.addNewRate(rateDto);
        rateProductService.addRateToProduct(productId, savedRate.getId());
    }

    public UserViewDto getUserViewByLogin(String username) throws NotFoundException {
        UserDto userByLogin = userService.getUserByLogin(username);
        return userViewDtoMapper.toDto(userByLogin);
    }

    public void updateUserView(Integer userId, UserViewDto user) throws NotFoundException, InvalidDataException {
        UserDto userDto = userService.getUserById(userId);
        Integer personalDataId = userDto.getPersonalDataId();
        CreateUpdatePersonalDataDto createUpdatePersonalDataDto = userViewDtoMapper.toPersonalDataDto(user);
        personalDataService.updatePersonalData(personalDataId, createUpdatePersonalDataDto); //todo data różnica jednego dnia przy edycji
    }


}
