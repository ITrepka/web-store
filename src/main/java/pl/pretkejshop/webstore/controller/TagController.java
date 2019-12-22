package pl.pretkejshop.webstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    TagService tagService;

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
