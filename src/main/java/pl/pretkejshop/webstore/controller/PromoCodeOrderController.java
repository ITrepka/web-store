//package pl.pretkejshop.webstore.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import pl.pretkejshop.webstore.service.dto.OrderDto;
//import pl.pretkejshop.webstore.service.dto.PromoCodeDto;
//import pl.pretkejshop.webstore.service.exception.NotFoundException;
//import pl.pretkejshop.webstore.service.services.PromoCodeOrderService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/rates")
//public class PromoCodeOrderController {
//    @Autowired
//    private PromoCodeOrderService promoCodeOrderService;
//
//    @GetMapping("/promoCodes/{promoCodeId}/orders")
//    public List<OrderDto> getOrdersWithPromoCode(@PathVariable Integer promoCodeId) throws NotFoundException {
//        return promoCodeOrderService.getOrdersWithPromoCode(promoCodeId);
//    }
//
//    @PostMapping("/orders/{orderId}/promoCodes/{promoCodeId}")
//    private OrderDto addPromoCodeToOrder(@PathVariable Integer orderId, @PathVariable Integer promoCodeId) throws NotFoundException {
//        return promoCodeOrderService.addPromoCodeToOrder(orderId, promoCodeId);
//    }
//
//    @DeleteMapping("/orders/{orderId}/promoCodes/{promoCodeId}")
//    private OrderDto deletePromoCodeFromOrder(@PathVariable Integer orderId, @PathVariable Integer promoCodeId) throws NotFoundException {
//        return promoCodeOrderService.deletePromoCodeFromOrder(orderId, promoCodeId);
//    }
//}
