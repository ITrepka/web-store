package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBasketDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.BasketService;

import java.util.List;

@RestController
@RequestMapping("api/v1/baskets")
public class BasketController {
    @Autowired
    BasketService basketService;

    @GetMapping
    public List<BasketDto> getAllBaskets() {
        return basketService.getAllBaskets();
    }

    @GetMapping("/{id}")
    public BasketDto getBasketById(@PathVariable int id) throws NotFoundException {
        return basketService.getBasketById(id);
    }

    @PostMapping
    public BasketDto addNewBasket(@RequestBody CreateUpdateBasketDto createBasketDto) throws InvalidDataException, NotFoundException {
        return basketService.addNewBasket(createBasketDto);
    }

    @PutMapping("/{id}")
    public BasketDto updateBasketById(@PathVariable int id, @RequestBody CreateUpdateBasketDto basketToUpdate) throws NotFoundException, InvalidDataException {
        return basketService.updateBasketById(id, basketToUpdate);
    }

    @DeleteMapping("/{id}")
    public BasketDto deleteBasketById(@PathVariable int id) throws NotFoundException {
        return basketService.deleteBasketById(id);
    }
}
