package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePaymentTypeDto;
import pl.pretkejshop.webstore.service.dto.PaymentTypeDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.PaymentTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-types")
public class PaymentTypeController {
    @Autowired
    PaymentTypeService paymentTypeService;

    @GetMapping
    public List<PaymentTypeDto> getAllPaymentTypes() {
        return paymentTypeService.getAllPaymentTypes();
    }

    @GetMapping("/{id}")
    public PaymentTypeDto getPaymentTypeById(@PathVariable int id) throws NotFoundException {
        return paymentTypeService.getPaymentTypeById(id);
    }

    @PostMapping
    public PaymentTypeDto addNewPaymentType(@RequestBody CreateUpdatePaymentTypeDto createPaymentTypeDto) throws NotFoundException {
        return paymentTypeService.addNewPaymentType(createPaymentTypeDto);
    }

    @PutMapping("/{id}")
    public PaymentTypeDto updatePaymentTypeById(@PathVariable int id, @RequestBody CreateUpdatePaymentTypeDto paymentTypeToUpdate) throws NotFoundException {
        return paymentTypeService.updatePaymentTypeById(id, paymentTypeToUpdate);
    }

    @DeleteMapping("/{id}")
    public PaymentTypeDto deletePaymentTypeById(@PathVariable int id) throws NotFoundException {
        return paymentTypeService.deletePaymentTypeById(id);
    }
}
