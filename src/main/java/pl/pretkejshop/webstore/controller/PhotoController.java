package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photo")
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
