package pl.pretkejshop.webstore.view.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.config.AuthenticationSystem;
import pl.pretkejshop.webstore.model.Basket;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBasketDto;
import pl.pretkejshop.webstore.service.dto.ProductCopyDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.BasketProductService;
import pl.pretkejshop.webstore.service.services.BasketService;
import pl.pretkejshop.webstore.service.services.UserService;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.mapper.BasketViewDtoMapper;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CartViewService {
    @Autowired
    private BasketService basketService;
    @Autowired
    private BasketProductService basketProductService;
    @Autowired
    private BasketViewDtoMapper basketViewDtoMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopViewService shopViewService;

    public BigDecimal calculatePriceForCartItems(Map<ProductViewDto, Integer> productsInBasket) {
        return productsInBasket.entrySet().stream()
                .map(entry -> entry.getKey().getSellingPrize().multiply(new BigDecimal(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BasketViewDto addProductToUserBasket(UserDto user, ProductViewDto product) throws InvalidDataException, NotFoundException {
        BasketDto basketDto;

        if (user.getBasketId() == null) {
            CreateUpdateBasketDto createBasketDto = new CreateUpdateBasketDto(user.getId());
            basketDto = basketService.addNewBasket(createBasketDto);
        } else {
            basketDto = basketService.getBasketById(user.getBasketId());
        }

        BasketDto basketWithAddedProduct = basketProductService.addProductToBasket(basketDto.getId(), product.getProductId());
        return basketViewDtoMapper.toViewDto(basketWithAddedProduct);
    }

    private BasketViewDto removeProductFromUserBasket(UserDto user, ProductViewDto product) throws NotFoundException {
        BasketDto basketDto = basketService.getBasketById(user.getBasketId());

        BasketDto basketWithAddedProduct = basketProductService.deleteProductFromBasket(basketDto.getId(), product.getProductId());
        return basketViewDtoMapper.toViewDto(basketWithAddedProduct);
    }

    public BasketViewDto updateProductQuantityInLoggedUserBasket(String username, Integer productId, Integer ratio) throws NotFoundException, InvalidDataException {
        UserDto user = userService.getUserByLogin(username);
        ProductViewDto productById = shopViewService.getProductById(productId);
        if (ratio == 1) {
            return addProductToUserBasket(user, productById);
        } else {
            return removeProductFromUserBasket(user, productById);
        }
    }

    public BasketViewDto updateProductQuantityInSessionBasket(BasketViewDto sessionCart, Integer productId, Integer ratio) throws NotFoundException, InvalidDataException {
        ProductViewDto product = shopViewService.getProductById(productId);
        Map<ProductViewDto, Integer> productsInBasket = sessionCart.getProductsInBasket();
        Integer currentProductQuantity = productsInBasket.get(product);
        if (ratio == 1) {
            if (currentProductQuantity + 1 > product.getNumberOfCopies()) {
                throw new InvalidDataException("We havent enough copies of this product");
            }
            productsInBasket.put(product, currentProductQuantity + 1);
        } else {
            if (currentProductQuantity - 1 < 1) {
                throw new InvalidDataException("You can't order less than one copy of the product");
            }
            productsInBasket.put(product, currentProductQuantity - 1);
        }
        BigDecimal priceForCartItems = calculatePriceForCartItems(productsInBasket);
        return new BasketViewDto(productsInBasket, priceForCartItems);
    }

    public BasketViewDto getCurrentCart(HttpSession session) {
        BasketViewDto basketViewDto;
        if (session.getAttribute("userCart") != null) {
            basketViewDto = (BasketViewDto) session.getAttribute("userCart");
        } else if (session.getAttribute("sessionCart") != null) {
            basketViewDto = (BasketViewDto) session.getAttribute("sessionCart");
        } else {
            basketViewDto = new BasketViewDto();
        }
        return basketViewDto;
    }
}
