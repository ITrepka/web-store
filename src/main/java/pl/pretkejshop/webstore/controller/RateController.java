package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rate")
public class RateController {
    @Autowired
    RateService rateService;

    @GetMapping
    public List<RateDto> getAllRates() {
        return rateService.getAllRates();
    }

    @GetMapping("/{id}")
    public RateDto getRateById(@PathVariable int id) throws NotFoundException {
        return rateService.getRateById(id);
    }

    @PostMapping
    public RateDto addNewRate(@RequestBody CreateUpdateRateDto createRateDto) throws AlreadyExistsException, InvalidDataException {
        return rateService.addNewRate(createRateDto);
    }

    @PutMapping("/{id}")
    public RateDto updateRateById(@PathVariable int id, @RequestBody CreateUpdateRateDto rateToUpdate) throws NotFoundException, InvalidDataException {
        return rateService.updateRateById(id, rateToUpdate);
    }

    @DeleteMapping("/{id}")
    public RateDto deleteRateById(@PathVariable int id) throws NotFoundException {
        return rateService.deleteRateById(id);
    }
}
