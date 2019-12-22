package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.DeliveryType;
import pl.pretkejshop.webstore.repository.DeliveryTypeRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateDeliveryTypeDto;
import pl.pretkejshop.webstore.service.dto.DeliveryTypeDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.DeliveryTypeDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryTypeService {
    @Autowired
    private DeliveryTypeRepository deliveryTypeRepository;
    @Autowired
    private DeliveryTypeDtoMapper deliveryTypeDtoMapper;

    public List<DeliveryTypeDto> getAllDeliveryTypes() {
        return deliveryTypeRepository.findAll().stream()
                .map(deliveryType -> deliveryTypeDtoMapper.toDto(deliveryType))
                .collect(Collectors.toList());
    }

    @Transactional
    public DeliveryTypeDto getDeliveryTypeById(int id) throws NotFoundException {
        return deliveryTypeRepository.findById(id)
                .map(deliveryType -> deliveryTypeDtoMapper.toDto(deliveryType))
                .orElseThrow(() -> new NotFoundException("Not found deliveryType with id = " + id));
    }

    @Transactional
    public DeliveryTypeDto addNewDeliveryType(CreateUpdateDeliveryTypeDto createDeliveryTypeDto) {
        DeliveryType deliveryType = deliveryTypeDtoMapper.toModel(createDeliveryTypeDto);
        deliveryType.setCreatedAt(OffsetDateTime.now());
        DeliveryType savedDeliveryType = deliveryTypeRepository.save(deliveryType);
        return deliveryTypeDtoMapper.toDto(savedDeliveryType);
    }

    @Transactional
    public DeliveryTypeDto updateDeliveryTypeById(int id, CreateUpdateDeliveryTypeDto deliveryTypeToUpdate) throws NotFoundException {
        DeliveryType deliveryType = deliveryTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found DeliveryType with id = " + id));
        deliveryType.setName(deliveryTypeToUpdate.getName());
        deliveryType.setPrice(deliveryTypeToUpdate.getPrice());
        deliveryType.setUpdatedAt(OffsetDateTime.now());
        DeliveryType savedDeliveryType = deliveryTypeRepository.save(deliveryType);
        return deliveryTypeDtoMapper.toDto(savedDeliveryType);
    }

    @Transactional
    public DeliveryTypeDto deleteDeliveryTypeById(int id) throws NotFoundException {
        DeliveryType deliveryType = deliveryTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found deliveryType with id = " + id));
        deliveryTypeRepository.deleteById(id);
        return deliveryTypeDtoMapper.toDto(deliveryType);
    }
}
