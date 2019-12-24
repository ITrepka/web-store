package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Comment;
import pl.pretkejshop.webstore.model.Rate;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.CommentRepository;
import pl.pretkejshop.webstore.repository.RateRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRateDto;
import pl.pretkejshop.webstore.service.dto.RateDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.RateDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private RateDtoMapper rateDtoMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<RateDto> getAllRates() {
        return rateRepository.findAll().stream()
                .map(rate -> rateDtoMapper.toDto(rate))
                .collect(Collectors.toList());
    }

    @Transactional
    public RateDto getRateById(int id) throws NotFoundException {
        return rateRepository.findById(id)
                .map(rate -> rateDtoMapper.toDto(rate))
                .orElseThrow(() -> new NotFoundException("Not found rate with id = " + id));
    }

    @Transactional
    public RateDto addNewRate(CreateUpdateRateDto createRateDto) throws NotFoundException {
        Rate rate = rateDtoMapper.toModel(createRateDto);
        rate.setCreatedAt(OffsetDateTime.now());
        Rate savedRate = rateRepository.save(rate);
        return rateDtoMapper.toDto(savedRate);
    }

    @Transactional
    public RateDto updateRateById(int id, CreateUpdateRateDto rateToUpdate) throws NotFoundException {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Rate with id = " + id));
        Integer userId = rateToUpdate.getUserId();
        User user = userId == null ? null : userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id = " + userId));
        Integer commentId = rateToUpdate.getCommentId();
        Comment comment = commentId == null ? null : commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + commentId));

        rate.setComment(comment);
        rate.setUser(user);
        rate.setRate(rateToUpdate.getRate());
        rate.setUpdatedAt(OffsetDateTime.now());
        Rate savedRate = rateRepository.save(rate);
        return rateDtoMapper.toDto(savedRate);
    }

    @Transactional
    public RateDto deleteRateById(int id) throws NotFoundException {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found rate with id = " + id));
        rateRepository.deleteById(id);
        return rateDtoMapper.toDto(rate);
    }
}
