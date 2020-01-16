package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.model.ShopPageViewModel;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ShopViewController {
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping("/shop/page/{pageNumber}")
    public ModelAndView displayShopView(@PathVariable Integer pageNumber,
                                        @RequestParam(required = false) String orderBy,
                                        @RequestParam(required = false) String s,
                                        @RequestParam(required = false) Integer min_price,
                                        @RequestParam(required = false) Integer max_price) throws NotFoundException {
        ShopPageViewModel shopPage = shopViewService.getShopPage(pageNumber, orderBy, s, min_price, max_price);
        ModelAndView mv = new ModelAndView("shop/page/" + pageNumber);
        mv.addObject("shopPage",  shopPage);
        return mv;
    }


    @GetMapping("/shop")
    public ModelAndView displayShopView(@RequestParam(required = false) String orderBy,
                                        @RequestParam(required = false) String s,
                                        @RequestParam(required = false) Integer min_price,
                                        @RequestParam(required = false) Integer max_price,
                                        @RequestParam(required = false) Integer page) throws NotFoundException {
        ModelAndView mv = new ModelAndView("shop");

        List<ProductViewDto> products = shopViewService.getAllProducts();
        mv.addObject("ourProducts", products.stream().limit(5).collect(Collectors.toList()));
        List<ProductViewDto> topRatedProducts = shopViewService.getTopRatedProducts(products).stream().limit(5).collect(Collectors.toList());
        mv.addObject("topRatedProducts", topRatedProducts);
        if (s != null) {
            products = shopViewService.searchProductByText(s, products);
        }
        if (orderBy != null) {
            products = shopViewService.sort(orderBy, products);
        }
        if (min_price != null && max_price != null) {
            products = shopViewService.filterBy(min_price, max_price, products);
        }

        page = page == null ? 1 : page;
        mv.addObject("page", page);

        int numberOfProductsOnPage = 12;
        double numberOfProducts = (double) products.size() / numberOfProductsOnPage;
        int amountOfPages = numberOfProducts % 1 == 0 ? (int)numberOfProducts : (int)numberOfProducts + 1;
        mv.addObject("amountOfPages", amountOfPages == 0 ? 1 : amountOfPages);
        int numberOfLastProductOnPage = Math.min(12 * page, products.size());
        String paragraphText = (1 + 12 * (page - 1)) + "-" + numberOfLastProductOnPage + " z " + products.size() + " produktów.";
        mv.addObject("paragraph1", paragraphText);
        products = products.stream().skip((page - 1) * 12).limit(12).collect(Collectors.toList());
        mv.addObject("products", products);
        return mv;
    }
}
