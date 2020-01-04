package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePromoCodeDto;
import pl.pretkejshop.webstore.service.dto.PromoCodeDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.PromoCodeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/promo_codes")
public class PromoCodeController {
    @Autowired
    private PromoCodeService promoCodeService;

    @GetMapping
    public List<PromoCodeDto> getAllPromoCodes() {
        return promoCodeService.getAllPromoCodes();
    }

    @GetMapping("/{id}")
    public PromoCodeDto getPromoCodeById(@PathVariable int id) throws NotFoundException {
        return promoCodeService.getPromoCodeById(id);
    }

    @PostMapping
    public PromoCodeDto addNewPromoCode(@RequestBody CreateUpdatePromoCodeDto createPromoCodeDto) throws AlreadyExistsException, InvalidDataException {
        return promoCodeService.addNewPromoCode(createPromoCodeDto);
    }

    @PutMapping("/{id}")
    public PromoCodeDto updatePromoCodeById(@PathVariable int id, @RequestBody CreateUpdatePromoCodeDto promoCodeToUpdate) throws NotFoundException, InvalidDataException {
        return promoCodeService.updatePromoCodeById(id, promoCodeToUpdate);
    }

    @DeleteMapping("/{id}")
    public PromoCodeDto deletePromoCodeById(@PathVariable int id) throws NotFoundException {
        return promoCodeService.deletePromoCodeById(id);
    }
}
