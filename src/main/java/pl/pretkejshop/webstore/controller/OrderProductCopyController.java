//package pl.pretkejshop.webstore.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import pl.pretkejshop.webstore.service.dto.OrderDto;
//import pl.pretkejshop.webstore.service.dto.ProductCopyDto;
//import pl.pretkejshop.webstore.service.exception.NotFoundException;
//import pl.pretkejshop.webstore.service.services.OrderProductCopyService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1")
//public class OrderProductCopyController {
//    @Autowired
//    private OrderProductCopyService orderProductCopyService;
//
//    @GetMapping("/orders/{orderId}/productCopies")
//    public List<ProductCopyDto> getOrderProductCopies(@PathVariable Integer orderId) throws NotFoundException {
//        return orderProductCopyService.getOrderProductCopies(orderId);
//    }
//
//    @PostMapping("/orders/{orderId}/product-copies/{productCopyId}")
//    private OrderDto addProductCopyToOrder(@PathVariable Integer orderId, @PathVariable Long productCopyId) throws NotFoundException {
//        return orderProductCopyService.addProductCopyToOrder(orderId, productCopyId);
//    }
//
//    @DeleteMapping("/orders/{orderId}/product-copies/{productCopyId}")
//    private OrderDto deleteProductCopyFromOrder(@PathVariable Integer orderId, @PathVariable Long productCopyId) throws NotFoundException {
//        return orderProductCopyService.deleteProductCopyFromOrder(orderId, productCopyId);
//    }
//}
