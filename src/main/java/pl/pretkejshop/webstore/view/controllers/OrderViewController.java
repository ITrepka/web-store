package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.view.service.services.CartViewService;

import javax.servlet.http.HttpSession;

@Controller
public class OrderViewController {
    @Autowired
    private CartViewService cartViewService;

    @GetMapping("/submit-your-order")
    public ModelAndView submitYourOrder(HttpSession session) {
        ModelAndView mv = new ModelAndView("submit-your-order");
        return cartViewService.getCurrentCart(session, mv);
    }

}
