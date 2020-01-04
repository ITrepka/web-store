package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.AdDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateAdDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.AdService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ads")
public class AdController {
    @Autowired
    private AdService adService;

    @GetMapping
    public List<AdDto> getAllAds() {
        return adService.getAllAds();
    }

    @GetMapping("/{id}")
    public AdDto getAdById(@PathVariable int id) throws NotFoundException {
        return adService.getAdById(id);
    }

    @PostMapping
    public AdDto addNewAd(@RequestBody CreateUpdateAdDto createAdDto) throws InvalidDataException {
        return adService.addNewAd(createAdDto);
    }

    @PutMapping("/{id}")
    public AdDto updateAd(@PathVariable int id, @RequestBody CreateUpdateAdDto updateAdDto) throws NotFoundException, InvalidDataException {
        return adService.updateAd(id, updateAdDto);
    }

    @DeleteMapping("/{id}")
    public AdDto deleteAd(@PathVariable int id) throws NotFoundException {
        return adService.deleteAd(id);
    }
}
