package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pretkejshop.webstore.config.AuthenticationSystem;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

@Controller
public class AddToCartController {
    @Autowired
    private ShopViewService shopViewService;
    @Autowired
    private UserService userService;

    @GetMapping("/add-to-cart/{id}")
    public String addProductToBasket(@PathVariable Integer id, HttpServletRequest request, HttpSession session, AuthenticationSystem authentication) throws NotFoundException, InvalidDataException {
        ProductViewDto product = shopViewService.getProductById(id);

        if (authentication.isLogged()) {
            String username = authentication.getName();
            UserDto user = userService.getUserByLogin(username);
            BasketViewDto userBasket = shopViewService.addProductToUserBasket(user, product);
            session.setAttribute("userCart", userBasket);
        }

        if (session.getAttribute("sessionCart") == null) {
            BasketViewDto newBasket = new BasketViewDto();
            Map<ProductViewDto, Integer> productsInBasket = newBasket.getProductsInBasket();
            productsInBasket.put(product, 1);
            newBasket.setProductsInBasket(productsInBasket);
            BigDecimal priceForCartItems = productsInBasket.entrySet().stream()
                    .map(entry -> entry.getKey().getSellingPrize().multiply(new BigDecimal(entry.getValue())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            newBasket.setPriceForCartItems(priceForCartItems);
            session.setAttribute("sessionCart", newBasket);
        } else {
            BasketViewDto sessionCart = (BasketViewDto) session.getAttribute("sessionCart");
            Map<ProductViewDto, Integer> productsInBasket = sessionCart.getProductsInBasket();
            BigDecimal priceForCartItems = sessionCart.getPriceForCartItems();
            if (productsInBasket.containsKey(product)) {
                productsInBasket.put(product, productsInBasket.get(product) + 1);
                priceForCartItems = priceForCartItems.add(product.getSellingPrize());
            } else {
                productsInBasket.put(product, 1);
                priceForCartItems = priceForCartItems.add(product.getSellingPrize());
            }
            sessionCart.setPriceForCartItems(priceForCartItems);
            session.setAttribute("sessionCart", sessionCart);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
