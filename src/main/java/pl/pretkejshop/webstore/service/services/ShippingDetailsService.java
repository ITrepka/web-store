package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.ShippingDetails;
import pl.pretkejshop.webstore.repository.ShippingDetailsRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateShippingDetailsDto;
import pl.pretkejshop.webstore.service.dto.ShippingDetailsDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.ShippingDetailsDtoMapper;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingDetailsService {
    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;
    @Autowired
    private ShippingDetailsDtoMapper shippingDetailsDtoMapper;

    public List<ShippingDetailsDto> getAllShippingDetails() {
        return shippingDetailsRepository.findAll().stream()
                .map(shippingDetails -> shippingDetailsDtoMapper.toDto(shippingDetails))
                .collect(Collectors.toList());
    }

    public ShippingDetailsDto getShippingDetailsById(long id) throws NotFoundException {
        ShippingDetails shippingDetails = shippingDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shipping details not found with id=" + id));
        return shippingDetailsDtoMapper.toDto(shippingDetails);
    }

    public ShippingDetailsDto addNewShippingDetails(CreateUpdateShippingDetailsDto createShippingDetailsDto) {
        ShippingDetails shippingDetails = shippingDetailsDtoMapper.toModel(createShippingDetailsDto);
        shippingDetails.setCreatedAt(OffsetDateTime.now());
        ShippingDetails savedShippingDetails = shippingDetailsRepository.save(shippingDetails);
        return shippingDetailsDtoMapper.toDto(savedShippingDetails);
    }

    public ShippingDetailsDto updateShippingDetails(long id, CreateUpdateShippingDetailsDto updateShippingDetailsDto) throws NotFoundException {
        ShippingDetails shippingDetails = shippingDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found shipping details with id=" + id));
        shippingDetails.setUpdatedAt(OffsetDateTime.now());
        shippingDetails.setName(updateShippingDetailsDto.getName());
        shippingDetails.setSurname(updateShippingDetailsDto.getSurname());
        shippingDetails.setAddress(updateShippingDetailsDto.getAddress());
        shippingDetails.setPhoneNumber(updateShippingDetailsDto.getPhoneNumber());
        ShippingDetails updatedShippingDetails = shippingDetailsRepository.save(shippingDetails);
        return shippingDetailsDtoMapper.toDto(updatedShippingDetails);
    }

    public ShippingDetailsDto deleteShippingDetails(long id) throws NotFoundException {
        ShippingDetails shippingDetails = shippingDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found shipping details with id=" + id));
        shippingDetailsRepository.deleteById(id);
        return shippingDetailsDtoMapper.toDto(shippingDetails);
    }
}
