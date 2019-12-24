package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Photo;
import pl.pretkejshop.webstore.repository.PhotoRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePhotoDto;
import pl.pretkejshop.webstore.service.dto.PhotoDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.PhotoDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PhotoDtoMapper photoDtoMapper;

    public List<PhotoDto> getAllPhotos() {
        return photoRepository.findAll().stream()
                .map(photo -> photoDtoMapper.toDto(photo))
                .collect(Collectors.toList());
    }

    @Transactional
    public PhotoDto getPhotoById(int id) throws NotFoundException {
        return photoRepository.findById(id)
                .map(photo -> photoDtoMapper.toDto(photo))
                .orElseThrow(() -> new NotFoundException("Not found photo with id = " + id));
    }

    @Transactional
    public PhotoDto addNewPhoto(CreateUpdatePhotoDto createPhotoDto) {
        Photo photo = photoDtoMapper.toModel(createPhotoDto);
        photo.setCreatedAt(OffsetDateTime.now());
        Photo savedPhoto = photoRepository.save(photo);
        return photoDtoMapper.toDto(savedPhoto);
    }

    @Transactional
    public PhotoDto updatePhotoById(int id, CreateUpdatePhotoDto photoToUpdate) throws NotFoundException {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Photo with id = " + id));

        photo.setSize(photoToUpdate.getSize());
        photo.setUrl(photoToUpdate.getUrl());
        photo.setUpdatedAt(OffsetDateTime.now());
        Photo savedPhoto = photoRepository.save(photo);
        return photoDtoMapper.toDto(savedPhoto);
    }

    @Transactional
    public PhotoDto deletePhotoById(int id) throws NotFoundException {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found photo with id = " + id));
        photoRepository.deleteById(id);
        return photoDtoMapper.toDto(photo);
    }
}
