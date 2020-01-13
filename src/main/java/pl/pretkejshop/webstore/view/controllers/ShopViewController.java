package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import java.util.List;

@Controller
public class ShopViewController {
    @Autowired
    private ShopViewService shopViewService;


    @GetMapping("/shop")
    public ModelAndView displayShopView() throws NotFoundException {
        List<ProductViewDto> products = shopViewService.getAllProducts();
        List<ProductViewDto> topRatedProducts = shopViewService.getTopRatedProducts(products);
        ModelAndView mv = new ModelAndView("shop");
        mv.addObject("products", products);
        mv.addObject("topRatedProducts", topRatedProducts);
        return mv;
    }
}
