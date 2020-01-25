package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.pretkejshop.webstore.config.AuthenticationSystem;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.CartViewService;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

@Controller
public class CartOperationsController {
    @Autowired
    private CartViewService cartViewService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping("/add-to-cart/{id}")
    public String addProductToBasket(@PathVariable Integer id, HttpServletRequest request,
                                     @RequestParam(required = false) String quantity,
                                     HttpSession session, AuthenticationSystem authentication) throws NotFoundException, InvalidDataException {
        ProductViewDto product = shopViewService.getProductById(id);
        Integer productQuantityToAdd = quantity == null ? 1 : Integer.parseInt(quantity);

        if (authentication.isLogged()) {
            String username = authentication.getName();
            UserDto user = userService.getUserByLogin(username);

            BasketViewDto userBasket = new BasketViewDto();
            for (int i = 0; i < productQuantityToAdd; i++) {
                userBasket = cartViewService.addProductToUserBasket(user, product);
            }
            session.setAttribute("userCart", userBasket);
        }

        if (session.getAttribute("sessionCart") == null) {
            BasketViewDto newBasket = new BasketViewDto();
            Map<ProductViewDto, Integer> productsInBasket = newBasket.getProductsInBasket();
            productsInBasket.put(product, productsInBasket.get(product) + productQuantityToAdd);
            newBasket.setProductsInBasket(productsInBasket);
            BigDecimal priceForCartItems = cartViewService.calculatePriceForCartItems(productsInBasket);
            newBasket.setPriceForCartItems(priceForCartItems);
            session.setAttribute("sessionCart", newBasket);
        } else {
            BasketViewDto sessionCart = (BasketViewDto) session.getAttribute("sessionCart");
            Map<ProductViewDto, Integer> productsInBasket = sessionCart.getProductsInBasket();
            BigDecimal priceForCartItems;
            if (productsInBasket.containsKey(product)) {
                productsInBasket.put(product, productsInBasket.get(product) + productQuantityToAdd);
                priceForCartItems = cartViewService.calculatePriceForCartItems(productsInBasket);
            } else {
                productsInBasket.put(product, productQuantityToAdd);
                priceForCartItems = cartViewService.calculatePriceForCartItems(productsInBasket);
            }
            sessionCart.setPriceForCartItems(priceForCartItems);
            session.setAttribute("sessionCart", sessionCart);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/update-products-quantity/{productId}")
    public String updateProductsQuantity(@PathVariable Integer productId,
                                         @RequestParam Integer ratio, HttpSession session,
                                         HttpServletRequest request,
                                         AuthenticationSystem authentication) throws NotFoundException, InvalidDataException {

        if (authentication.isLogged()) {
            String username = authentication.getName();
            BasketViewDto userCart = cartViewService.updateProductQuantityInLoggedUserBasket(username, productId, ratio);
            session.setAttribute("userCart", userCart);
        } else {
            BasketViewDto sessionCart = (BasketViewDto)session.getAttribute("sessionCart");
            sessionCart = cartViewService.updateProductQuantityInSessionBasket(sessionCart, productId, ratio);
            session.setAttribute("sessionCart", sessionCart);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
