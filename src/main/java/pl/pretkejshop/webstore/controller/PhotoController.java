package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePhotoDto;
import pl.pretkejshop.webstore.service.dto.PhotoDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.PhotoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @GetMapping
    public List<PhotoDto> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    public PhotoDto getPhotoById(@PathVariable int id) throws NotFoundException {
        return photoService.getPhotoById(id);
    }

    @PostMapping
    public PhotoDto addNewPhoto(@RequestBody CreateUpdatePhotoDto createPhotoDto) throws AlreadyExistsException, InvalidDataException {
        return photoService.addNewPhoto(createPhotoDto);
    }

    @PutMapping("/{id}")
    public PhotoDto updatePhotoById(@PathVariable int id, @RequestBody CreateUpdatePhotoDto photoToUpdate) throws NotFoundException, InvalidDataException {
        return photoService.updatePhotoById(id, photoToUpdate);
    }

    @DeleteMapping("/{id}")
    public PhotoDto deletePhotoById(@PathVariable int id) throws NotFoundException {
        return photoService.deletePhotoById(id);
    }
}
