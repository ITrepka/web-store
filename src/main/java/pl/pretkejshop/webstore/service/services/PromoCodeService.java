package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.PromoCode;
import pl.pretkejshop.webstore.repository.PromoCodeRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePromoCodeDto;
import pl.pretkejshop.webstore.service.dto.PromoCodeDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PromoCodeDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromoCodeService {
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    @Autowired
    private PromoCodeDtoMapper promoCodeDtoMapper;

    public List<PromoCodeDto> getAllPromoCodes() {
        return promoCodeRepository.findAll().stream()
                .map(promoCode -> promoCodeDtoMapper.toDto(promoCode))
                .collect(Collectors.toList());
    }

    @Transactional
    public PromoCodeDto getPromoCodeById(int id) throws NotFoundException {
        return promoCodeRepository.findById(id)
                .map(promoCode -> promoCodeDtoMapper.toDto(promoCode))
                .orElseThrow(() -> new NotFoundException("Not found promoCode with id = " + id));
    }

    @Transactional
    public PromoCodeDto addNewPromoCode(CreateUpdatePromoCodeDto createPromoCodeDto) {
        PromoCode promoCode = promoCodeDtoMapper.toModel(createPromoCodeDto);
        promoCode.setCreatedAt(OffsetDateTime.now());
        PromoCode savedPromoCode = promoCodeRepository.save(promoCode);
        return promoCodeDtoMapper.toDto(savedPromoCode);
    }

    @Transactional
    public PromoCodeDto updatePromoCodeById(int id, CreateUpdatePromoCodeDto promoCodeToUpdate) throws NotFoundException {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found PromoCode with id = " + id));

        promoCode.setName(promoCodeToUpdate.getName());
        promoCode.setDescription(promoCodeToUpdate.getDescription());
        promoCode.setDiscount(promoCodeToUpdate.getDiscount());
        promoCode.setUpdatedAt(OffsetDateTime.now());
        PromoCode savedPromoCode = promoCodeRepository.save(promoCode);
        return promoCodeDtoMapper.toDto(savedPromoCode);
    }

    @Transactional
    public PromoCodeDto deletePromoCodeById(int id) throws NotFoundException {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found promoCode with id = " + id));
        promoCodeRepository.deleteById(id);
        return promoCodeDtoMapper.toDto(promoCode);
    }
}
