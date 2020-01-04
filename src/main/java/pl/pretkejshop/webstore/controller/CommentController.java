package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CommentDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCommentDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable int id) throws NotFoundException {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public CommentDto addNewComment(@RequestBody CreateUpdateCommentDto createCommentDto) throws AlreadyExistsException, InvalidDataException {
        return commentService.addNewComment(createCommentDto);
    }

    @PutMapping("/{id}")
    public CommentDto updateCommentById(@PathVariable int id, @RequestBody CreateUpdateCommentDto commentToUpdate) throws NotFoundException, InvalidDataException {
        return commentService.updateCommentById(id, commentToUpdate);
    }

    @DeleteMapping("/{id}")
    public CommentDto deleteCommentById(@PathVariable int id) throws NotFoundException {
        return commentService.deleteCommentById(id);
    }
}
