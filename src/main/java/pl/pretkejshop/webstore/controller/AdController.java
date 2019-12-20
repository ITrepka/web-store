package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.AdDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateAdDto;
import pl.pretkejshop.webstore.service.exception.AdInvalidDataException;
import pl.pretkejshop.webstore.service.exception.AdNotFoundException;
import pl.pretkejshop.webstore.service.services.AdService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ad")
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping
    public List<AdDto> getAllAds() {
        return adService.getAllAds();
    }

    @GetMapping("/{id}")
    public AdDto getAdById(@PathVariable int id) throws AdNotFoundException {
        return adService.getAdById(id);
    }

    @PostMapping
    public AdDto addNewAd(@RequestBody CreateUpdateAdDto createAdDto) throws AdInvalidDataException {
        return adService.addNewAd(createAdDto);
    }

    @PutMapping("/{id}")
    public AdDto updateAd(@PathVariable int id, @RequestBody CreateUpdateAdDto updateAdDto) throws AdNotFoundException, AdInvalidDataException {
        return adService.updateAd(id, updateAdDto);
    }

    @DeleteMapping("/{id}")
    public AdDto deleteAd(@PathVariable int id) throws AdNotFoundException {
        return adService.deleteAd(id);
    }
}
