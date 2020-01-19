package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCommentDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

@Controller
public class AddRateToProductController {
    @Autowired
    private ShopViewService shopViewService;

    //todo access only for logged in users
    @PostMapping("/product/{productId}/add-rate")
    public ModelAndView addRateToProduct(@ModelAttribute(name = "comment") CreateUpdateCommentDto comment,
                                         @RequestParam Integer rate,
                                         @PathVariable Integer productId, Authentication authentication) throws NotFoundException {
        shopViewService.addRateToProduct(rate, comment, productId, authentication.getName());
        ModelAndView mv = new ModelAndView("redirect:/product/product/" + productId);
        return mv;
    }
}
