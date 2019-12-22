package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment_type")
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
    public PaymentTypeDto addNewPaymentType(@RequestBody CreateUpdatePaymentTypeDto createPaymentTypeDto) throws AlreadyExistsException, InvalidDataException {
        return paymentTypeService.addNewPaymentType(createPaymentTypeDto);
    }

    @PutMapping("/{id}")
    public PaymentTypeDto updatePaymentTypeById(@PathVariable int id, @RequestBody CreateUpdatePaymentTypeDto paymentTypeToUpdate) throws NotFoundException, InvalidDataException {
        return paymentTypeService.updatePaymentTypeById(id, paymentTypeToUpdate);
    }

    @DeleteMapping("/{id}")
    public PaymentTypeDto deletePaymentTypeById(@PathVariable int id) throws NotFoundException {
        return paymentTypeService.deletePaymentTypeById(id);
    }
}
