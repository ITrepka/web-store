package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/promo_code")
public class PromoCodeController {
    @Autowired
    PromoCodeService promoCodeService;

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
