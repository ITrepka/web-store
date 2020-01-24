package pl.pretkejshop.webstore.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartViewController {
    @GetMapping
    public ModelAndView displayYourCart(HttpSession session) {
        ModelAndView mv = new ModelAndView("cart");

        if (session.getAttribute("userCart") != null) {
            mv.addObject("cart" , (BasketViewDto)session.getAttribute("userCart"));
        } else if (session.getAttribute("sessionCart") != null) {
            mv.addObject("cart" , (BasketViewDto)session.getAttribute("sessionCart"));
        } else {
            mv.addObject("cart", new BasketViewDto());
        }
        return mv;
    }
}
