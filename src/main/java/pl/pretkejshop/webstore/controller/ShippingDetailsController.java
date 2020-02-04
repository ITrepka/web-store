package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.ShippingDetailsDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateShippingDetailsDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ShippingDetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping-details")
public class ShippingDetailsController {
    @Autowired
    private ShippingDetailsService shippingDetailsService;

    @GetMapping
    public List<ShippingDetailsDto> getAllShippingDetails() {
        return shippingDetailsService.getAllShippingDetails();
    }

    @GetMapping("/{id}")
    public ShippingDetailsDto getShippingDetailsById(@PathVariable long id) throws NotFoundException {
        return shippingDetailsService.getShippingDetailsById(id);
    }

    @PostMapping
    public ShippingDetailsDto addNewShippingDetails(@RequestBody CreateUpdateShippingDetailsDto createShippingDetailsDto) throws InvalidDataException, NotFoundException {
        return shippingDetailsService.addNewShippingDetails(createShippingDetailsDto);
    }

    @PutMapping("/{id}")
    public ShippingDetailsDto updateShippingDetails(@PathVariable long id, @RequestBody CreateUpdateShippingDetailsDto updateShippingDetailsDto) throws NotFoundException, InvalidDataException {
        return shippingDetailsService.updateShippingDetails(id, updateShippingDetailsDto);
    }

    @DeleteMapping("/{id}")
    public ShippingDetailsDto deleteShippingDetails(@PathVariable long id) throws NotFoundException {
        return shippingDetailsService.deleteShippingDetails(id);
    }
}
