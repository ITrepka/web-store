package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.Order;
import pl.pretkejshop.webstore.service.dto.*;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
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
        BasketViewDto currentCart = cartViewService.getCurrentCart(session);
        mv.addObject("cart", currentCart);
        List<DeliveryTypeDto> deliveryTypesList = orderViewService.getDeliveryTypes();
        List<PaymentTypeDto> paymentTypesList = orderViewService.getPaymentTypesList();
        mv.addObject("deliveryTypesList", deliveryTypesList);
        mv.addObject("shippingDetails", new CreateUpdateShippingDetailsDto());
        mv.addObject("paymentTypesList", paymentTypesList);
        return mv;
    }

    @PostMapping("/submit-your-order")
    public ModelAndView submitOrder(@ModelAttribute CreateUpdateShippingDetailsDto shippingDetailsDto,
                                    @RequestParam String deliveryTypeId, @RequestParam String paymentTypeId,
                                    @RequestParam String promoCode, HttpSession session) throws NotFoundException {
        Integer deliveryTypeIdAsInt = Integer.parseInt(deliveryTypeId);
        Integer paymentTypeIdAsInt = Integer.valueOf(paymentTypeId);
        ModelAndView mv = new ModelAndView("order-info");
        BasketViewDto currentCart = cartViewService.getCurrentCart(session);
        OrderDto orderDto = orderViewService.submitTheOrder(shippingDetailsDto, currentCart, deliveryTypeIdAsInt, paymentTypeIdAsInt, promoCode);
        return mv;
    }

}
