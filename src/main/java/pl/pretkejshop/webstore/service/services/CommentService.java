package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Comment;
import pl.pretkejshop.webstore.repository.CommentRepository;
import pl.pretkejshop.webstore.service.dto.CommentDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCommentDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.CommentDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentDtoMapper commentDtoMapper;

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> commentDtoMapper.toDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto getCommentById(int id) throws NotFoundException {
        return commentRepository.findById(id)
                .map(comment -> commentDtoMapper.toDto(comment))
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + id));
    }

    @Transactional
    public CommentDto addNewComment(CreateUpdateCommentDto createCommentDto) {
        Comment comment = commentDtoMapper.toModel(createCommentDto);
        comment.setCreatedAt(OffsetDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentDtoMapper.toDto(savedComment);
    }

    @Transactional
    public CommentDto updateCommentById(int id, CreateUpdateCommentDto commentToUpdate) throws NotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Comment with id = " + id));
        comment.setText(commentToUpdate.getText());
        comment.setUpdatedAt(OffsetDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentDtoMapper.toDto(savedComment);
    }

    @Transactional
    public CommentDto deleteCommentById(int id) throws NotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + id));
        commentRepository.deleteById(id);
        return commentDtoMapper.toDto(comment);
    }
}
