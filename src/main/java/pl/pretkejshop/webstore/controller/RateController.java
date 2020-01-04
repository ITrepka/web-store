package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRateDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.RateService;

import java.util.List;

@RestController
@RequestMapping("api/v1/rates")
public class RateController {
    @Autowired
    private RateService rateService;

    @GetMapping
    public List<RateDto> getAllRates() {
        return rateService.getAllRates();
    }

    @GetMapping("/{id}")
    public RateDto getRateById(@PathVariable int id) throws NotFoundException {
        return rateService.getRateById(id);
    }

    @PostMapping
    public RateDto addNewRate(@RequestBody CreateUpdateRateDto createRateDto) throws NotFoundException {
        return rateService.addNewRate(createRateDto);
    }

    @PutMapping("/{id}")
    public RateDto updateRateById(@PathVariable int id, @RequestBody CreateUpdateRateDto rateToUpdate) throws NotFoundException {
        return rateService.updateRateById(id, rateToUpdate);
    }

    @DeleteMapping("/{id}")
    public RateDto deleteRateById(@PathVariable int id) throws NotFoundException {
        return rateService.deleteRateById(id);
    }
}
