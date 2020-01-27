package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.PaymentType;
import pl.pretkejshop.webstore.repository.PaymentTypeRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePaymentTypeDto;
import pl.pretkejshop.webstore.service.dto.PaymentTypeDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PaymentTypeDtoMapper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private PaymentTypeDtoMapper paymentTypeDtoMapper;

//    @PostConstruct
//    public void init(){
//        paymentTypeRepository.save(new PaymentType(null, "PRZELEWY24", OffsetDateTime.now(), null, new ArrayList<>()));
//        paymentTypeRepository.save(new PaymentType(null, "TRADYCYJNY PRZELEW", OffsetDateTime.now(), null, new ArrayList<>()));
//    }


    public List<PaymentTypeDto> getAllPaymentTypes() {
        return paymentTypeRepository.findAll().stream()
                .map(paymentType -> paymentTypeDtoMapper.toDto(paymentType))
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentTypeDto getPaymentTypeById(int id) throws NotFoundException {
        return paymentTypeRepository.findById(id)
                .map(paymentType -> paymentTypeDtoMapper.toDto(paymentType))
                .orElseThrow(() -> new NotFoundException("Not found paymentType with id = " + id));
    }

    @Transactional
    public PaymentTypeDto addNewPaymentType(CreateUpdatePaymentTypeDto createPaymentTypeDto) throws NotFoundException {
        PaymentType paymentType = paymentTypeDtoMapper.toModel(createPaymentTypeDto);
        paymentType.setCreatedAt(OffsetDateTime.now());
        PaymentType savedPaymentType = paymentTypeRepository.save(paymentType);
        return paymentTypeDtoMapper.toDto(savedPaymentType);
    }

    @Transactional
    public PaymentTypeDto updatePaymentTypeById(int id, CreateUpdatePaymentTypeDto paymentTypeToUpdate) throws NotFoundException {
        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found PaymentType with id = " + id));

        paymentType.setPaymentType(paymentTypeToUpdate.getPaymentType());
        paymentType.setUpdatedAt(OffsetDateTime.now());
        PaymentType savedPaymentType = paymentTypeRepository.save(paymentType);
        return paymentTypeDtoMapper.toDto(savedPaymentType);
    }

    @Transactional
    public PaymentTypeDto deletePaymentTypeById(int id) throws NotFoundException {
        PaymentType paymentType = paymentTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found paymentType with id = " + id));
        paymentTypeRepository.deleteById(id);
        return paymentTypeDtoMapper.toDto(paymentType);
    }
}
