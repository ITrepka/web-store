package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Comment;
import pl.pretkejshop.webstore.repository.CommentRepository;
import pl.pretkejshop.webstore.service.dto.CommentDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCommentDto;

@Service
public class CommentDtoMapper {
@Autowired
    private CommentRepository commentRepository;

    public CommentDto toDto(Comment comment) {
        Integer rateId = comment.getRate() == null ? null : comment.getRate().getId();
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .rateId(rateId)
                .build();
    }

    public Comment toModel(CreateUpdateCommentDto createCommentDto) {
        return Comment.builder()
                .id(null)
                .text(createCommentDto.getText())
                .createdAt(null)
                .updatedAt(null)
                .rate(null)
                .build();
    }
}
