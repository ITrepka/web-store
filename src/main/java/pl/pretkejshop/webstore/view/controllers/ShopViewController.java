package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class ShopViewController {
    @Autowired
    private ShopViewService shopViewService;


    @GetMapping("/shop")
    public ModelAndView displayShopView(@RequestParam(required = false) String orderBy,
                                        @RequestParam(required = false) String s,
                                        @RequestParam(required = false) Integer min_price,
                                        @RequestParam(required = false) Integer max_price) throws NotFoundException {
        List<ProductViewDto> products = shopViewService.getAllProducts();
        List<ProductViewDto> topRatedProducts = shopViewService.getTopRatedProducts(products);
        if (s != null) {
            products = shopViewService.searchProductByText(s, products);
        }
        if (orderBy != null) {
            products = shopViewService.sort(orderBy, products);
        }
        if (min_price != null && max_price != null) {
            products = shopViewService.filterBy(min_price, max_price, products);
        }
        ModelAndView mv = new ModelAndView("shop");
        mv.addObject("products", products);
        mv.addObject("topRatedProducts", topRatedProducts);
        return mv;
    }
}
