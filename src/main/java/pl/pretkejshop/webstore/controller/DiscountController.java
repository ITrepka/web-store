package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateDiscountDto;
import pl.pretkejshop.webstore.service.dto.DiscountDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
public class DiscountController {
    @Autowired
    DiscountService discountService;

    @GetMapping
    public List<DiscountDto> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @GetMapping("/{id}")
    public DiscountDto getDiscountById(@PathVariable int id) throws NotFoundException {
        return discountService.getDiscountById(id);
    }

    @PostMapping
    public DiscountDto addNewDiscount(@RequestBody CreateUpdateDiscountDto createDiscountDto) throws AlreadyExistsException, InvalidDataException {
        return discountService.addNewDiscount(createDiscountDto);
    }

    @PutMapping("/{id}")
    public DiscountDto updateDiscountById(@PathVariable int id, @RequestBody CreateUpdateDiscountDto discountToUpdate) throws NotFoundException, InvalidDataException {
        return discountService.updateDiscountById(id, discountToUpdate);
    }

    @DeleteMapping("/{id}")
    public DiscountDto deleteDiscountById(@PathVariable int id) throws NotFoundException {
        return discountService.deleteDiscountById(id);
    }
}
