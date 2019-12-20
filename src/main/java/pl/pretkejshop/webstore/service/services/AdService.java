package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pretkejshop.webstore.model.Ad;
import pl.pretkejshop.webstore.repository.AdRepository;
import pl.pretkejshop.webstore.service.dto.AdDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateAdDto;
import pl.pretkejshop.webstore.service.exception.AdInvalidDataException;
import pl.pretkejshop.webstore.service.exception.AdNotFoundException;
import pl.pretkejshop.webstore.service.mapper.AdMapper;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private AdMapper adMapper;

    @PostConstruct
    public void init() throws AdInvalidDataException {
        addNewAd(new CreateUpdateAdDto(new BigDecimal(200), "Google", "Search something"));
        addNewAd(new CreateUpdateAdDto(new BigDecimal(100), "Allegro", "Go buy as much as you can"));
    }

    @Transactional
    public List<AdDto> getAllAds() {
        return adRepository.findAll().stream()
                .map(ad -> adMapper.toDto(ad))
                .collect(Collectors.toList());
    }

    @Transactional
    public AdDto getAdById(int id) throws AdNotFoundException {
        return adRepository.findById(id)
                .map(ad -> adMapper.toDto(ad))
                .orElseThrow(AdNotFoundException::new);
    }

    @Transactional
    public AdDto addNewAd(CreateUpdateAdDto createAdDto) throws AdInvalidDataException {
        validCreateUpdateAd(createAdDto);
        Ad ad = adMapper.toModel(createAdDto);
        ad.setCreatedAt(OffsetDateTime.now());
        Ad savedAd = adRepository.save(ad);
        return adMapper.toDto(savedAd);
    }

    @Transactional
    public AdDto updateAd(int id, CreateUpdateAdDto updateAdDto) throws AdNotFoundException, AdInvalidDataException {
        validCreateUpdateAd(updateAdDto);
        Ad ad = adRepository.findById(id)
                .orElseThrow(AdNotFoundException::new);
        ad.setPrice(updateAdDto.getPrice());
        ad.setTitle(updateAdDto.getTitle());
        ad.setText(updateAdDto.getText());
        ad.setUpdatedAt(OffsetDateTime.now());
        Ad savedAd = adRepository.save(ad);
        return adMapper.toDto(savedAd);
    }

    @Transactional
    public AdDto deleteAd(int id) throws AdNotFoundException {
        System.out.println("jestem w metodzie");
        Ad ad = adRepository.findById(id)
                .orElseThrow(AdNotFoundException::new);
        adRepository.delete(ad);
        System.out.print("usunalem wiadomosc");
        return adMapper.toDto(ad);
    }

    private void validCreateUpdateAd(CreateUpdateAdDto createAdDto) throws AdInvalidDataException {
        if (createAdDto.getText() == null || createAdDto.getPrice() == null || createAdDto.getTitle() == null ||
        createAdDto.getText().length() < 5 || createAdDto.getPrice().doubleValue() < 0 || createAdDto.getTitle().length() < 3) {
            throw new AdInvalidDataException();
        }
    }
}
