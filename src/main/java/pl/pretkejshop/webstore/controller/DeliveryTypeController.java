package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateDeliveryTypeDto;
import pl.pretkejshop.webstore.service.dto.DeliveryTypeDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.DeliveryTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery_type")
public class DeliveryTypeController {
    @Autowired
    DeliveryTypeService deliveryTypeService;

    @GetMapping
    public List<DeliveryTypeDto> getAllDeliveryTypes() {
        return deliveryTypeService.getAllDeliveryTypes();
    }

    @GetMapping("/{id}")
    public DeliveryTypeDto getDeliveryTypeById(@PathVariable int id) throws NotFoundException {
        return deliveryTypeService.getDeliveryTypeById(id);
    }

    @PostMapping
    public DeliveryTypeDto addNewDeliveryType(@RequestBody CreateUpdateDeliveryTypeDto createDeliveryTypeDto) throws AlreadyExistsException, InvalidDataException {
        return deliveryTypeService.addNewDeliveryType(createDeliveryTypeDto);
    }

    @PutMapping("/{id}")
    public DeliveryTypeDto updateDeliveryTypeById(@PathVariable int id, @RequestBody CreateUpdateDeliveryTypeDto deliveryTypeToUpdate) throws NotFoundException, InvalidDataException {
        return deliveryTypeService.updateDeliveryTypeById(id, deliveryTypeToUpdate);
    }

    @DeleteMapping("/{id}")
    public DeliveryTypeDto deleteDeliveryTypeById(@PathVariable int id) throws NotFoundException {
        return deliveryTypeService.deleteDeliveryTypeById(id);
    }
}
