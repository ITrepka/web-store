package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Comment;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.model.Rate;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.CommentRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRateDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateDtoMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public RateDto toDto(Rate rate) {
        Integer userId = rate.getUser() == null ? null : rate.getUser().getId();
        Integer commentId = rate.getComment() == null ? null : rate.getId();
        List<Integer> productsIds = rate.getProducts() == null ? null :
                rate.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        return RateDto.builder()
                .id(rate.getId())
                .rate(rate.getRate())
                .createdAt(rate.getCreatedAt())
                .updatedAt(rate.getUpdatedAt())
                .userId(userId)
                .commentId(commentId)
                .productsIds(productsIds)
                .build();
    }

    public Rate toModel(CreateUpdateRateDto createRateDto) throws NotFoundException {
        Integer userId = createRateDto.getUserId();
        User user = userId == null ? null : userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        Integer commentId = createRateDto.getCommentId();
        Comment comment = commentId == null ? null : commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));

        return Rate.builder()
                .id(null)
                .createdAt(null)
                .updatedAt(null)
                .rate(createRateDto.getRate())
                .user(user)
                .comment(comment)
                .products(null) // todo
                .build();
    }
}
