package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Brand;
import pl.pretkejshop.webstore.repository.BrandRepository;
import pl.pretkejshop.webstore.service.dto.BrandDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBrandDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.BrandDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandDtoMapper brandDtoMapper;

    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> brandDtoMapper.toDto(brand))
                .collect(Collectors.toList());
    }

    @Transactional
    public BrandDto getBrandById(int id) throws NotFoundException {
        return brandRepository.findById(id)
                .map(brand -> brandDtoMapper.toDto(brand))
                .orElseThrow(() -> new NotFoundException("Not found brand with id = " + id));
    }

    @Transactional
    public BrandDto addNewBrand(CreateUpdateBrandDto createBrandDto) {
        Brand brand = brandDtoMapper.toModel(createBrandDto);
        brand.setCreatedAt(OffsetDateTime.now());
        Brand savedBrand = brandRepository.save(brand);
        return brandDtoMapper.toDto(savedBrand);
    }

    @Transactional
    public BrandDto updateBrandById(int id, CreateUpdateBrandDto brandToUpdate) throws NotFoundException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Brand with id = " + id));
        brand.setName(brandToUpdate.getName());
        brand.setUpdatedAt(OffsetDateTime.now());
        Brand savedBrand = brandRepository.save(brand);
        return brandDtoMapper.toDto(savedBrand);
    }

    @Transactional
    public BrandDto deleteBrandById(int id) throws NotFoundException {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found brand with id = " + id));
        brandRepository.deleteById(id);
        return brandDtoMapper.toDto(brand);
    }
}
