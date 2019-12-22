package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Discount;
import pl.pretkejshop.webstore.repository.DiscountRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateDiscountDto;
import pl.pretkejshop.webstore.service.dto.DiscountDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.DiscountDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    @Autowired
    private DiscountDtoMapper discountDtoMapper;

    public List<DiscountDto> getAllDiscounts() {
        return discountRepository.findAll().stream()
                .map(discount -> discountDtoMapper.toDto(discount))
                .collect(Collectors.toList());
    }

    @Transactional
    public DiscountDto getDiscountById(int id) throws NotFoundException {
        return discountRepository.findById(id)
                .map(discount -> discountDtoMapper.toDto(discount))
                .orElseThrow(() -> new NotFoundException("Not found discount with id = " + id));
    }

    @Transactional
    public DiscountDto addNewDiscount(CreateUpdateDiscountDto createDiscountDto) {
        Discount discount = discountDtoMapper.toModel(createDiscountDto);
        discount.setCreatedAt(OffsetDateTime.now());
        Discount savedDiscount = discountRepository.save(discount);
        return discountDtoMapper.toDto(savedDiscount);
    }

    @Transactional
    public DiscountDto updateDiscountById(int id, CreateUpdateDiscountDto discountToUpdate) throws NotFoundException {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Discount with id = " + id));
        discount.setDescription(discountToUpdate.getDescription());
        discount.setPercentageValueReduction(discountToUpdate.getPercentageValueReduction());
        discount.setUpdatedAt(OffsetDateTime.now());
        Discount savedDiscount = discountRepository.save(discount);
        return discountDtoMapper.toDto(savedDiscount);
    }

    @Transactional
    public DiscountDto deleteDiscountById(int id) throws NotFoundException {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found discount with id = " + id));
        discountRepository.deleteById(id);
        return discountDtoMapper.toDto(discount);
    }
}
