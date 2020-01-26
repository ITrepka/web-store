package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.service.dto.DeliveryTypeDto;
import pl.pretkejshop.webstore.view.service.services.CartViewService;
import pl.pretkejshop.webstore.view.service.services.OrderViewService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderViewController {
    @Autowired
    private CartViewService cartViewService;
    @Autowired
    private OrderViewService orderViewService;

    @GetMapping("/submit-your-order")
    public ModelAndView submitYourOrder(HttpSession session) {
        ModelAndView mv = new ModelAndView("submit-your-order");
        mv = cartViewService.getCurrentCart(session, mv);
        List<DeliveryTypeDto> deliveryTypesList = orderViewService.getDeliveryTypes();
        mv.addObject("deliveryTypesList");
        return mv;
    }

}
