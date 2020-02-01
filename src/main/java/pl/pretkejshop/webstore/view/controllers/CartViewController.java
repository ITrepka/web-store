package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.services.CartViewService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartViewController {
    @Autowired
    private CartViewService cartViewService;

    @GetMapping
    public ModelAndView displayYourCart(HttpSession session) {
        ModelAndView mv = new ModelAndView("cart");

        BasketViewDto currentCart = cartViewService.getCurrentCart(session);
        mv.addObject("cart", currentCart);
        return mv;
    }
}
