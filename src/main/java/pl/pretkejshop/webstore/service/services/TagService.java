package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Tag;
import pl.pretkejshop.webstore.repository.TagRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateTagDto;
import pl.pretkejshop.webstore.service.dto.TagDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.TagDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagDtoMapper tagDtoMapper;

    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tag -> tagDtoMapper.toDto(tag))
                .collect(Collectors.toList());
    }

    @Transactional
    public TagDto getTagById(int id) throws NotFoundException {
        return tagRepository.findById(id)
                .map(tag -> tagDtoMapper.toDto(tag))
                .orElseThrow(() -> new NotFoundException("Not found tag with id = " + id));
    }

    @Transactional
    public TagDto addNewTag(CreateUpdateTagDto createTagDto) {
        Tag tag = tagDtoMapper.toModel(createTagDto);
        tag.setCreatedAt(OffsetDateTime.now());
        Tag savedTag = tagRepository.save(tag);
        return tagDtoMapper.toDto(savedTag);
    }

    @Transactional
    public TagDto updateTagById(int id, CreateUpdateTagDto tagToUpdate) throws NotFoundException {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Tag with id = " + id));

        tag.setName(tagToUpdate.getName());
        tag.setUpdatedAt(OffsetDateTime.now());
        Tag savedTag = tagRepository.save(tag);
        return tagDtoMapper.toDto(savedTag);
    }

    @Transactional
    public TagDto deleteTagById(int id) throws NotFoundException {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found tag with id = " + id));
        tagRepository.deleteById(id);
        return tagDtoMapper.toDto(tag);
    }
}
