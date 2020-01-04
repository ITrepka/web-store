package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateTagDto;
import pl.pretkejshop.webstore.service.dto.TagDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<TagDto> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable int id) throws NotFoundException {
        return tagService.getTagById(id);
    }

    @PostMapping
    public TagDto addNewTag(@RequestBody CreateUpdateTagDto createTagDto) throws AlreadyExistsException, InvalidDataException {
        return tagService.addNewTag(createTagDto);
    }

    @PutMapping("/{id}")
    public TagDto updateTagById(@PathVariable int id, @RequestBody CreateUpdateTagDto tagToUpdate) throws NotFoundException, InvalidDataException {
        return tagService.updateTagById(id, tagToUpdate);
    }

    @DeleteMapping("/{id}")
    public TagDto deleteTagById(@PathVariable int id) throws NotFoundException {
        return tagService.deleteTagById(id);
    }
}
