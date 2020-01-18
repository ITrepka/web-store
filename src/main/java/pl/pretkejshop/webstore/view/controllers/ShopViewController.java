package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.view.model.ShopPageViewModel;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

@Controller
public class ShopViewController {
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping("/shop")
    public ModelAndView displayShopView(@RequestParam(required = false) String orderBy,
                                        @RequestParam(required = false) String s,
                                        @RequestParam(required = false) Integer min_price,
                                        @RequestParam(required = false) Integer max_price,
                                        @RequestParam(required = false) Integer page) throws NotFoundException {
        ShopPageViewModel shopPage = shopViewService.getShopPage(page, orderBy, s, min_price, max_price);
        ModelAndView mv = new ModelAndView("shop");
        mv.addObject("shopPage",  shopPage);
        return mv;
    }
}
