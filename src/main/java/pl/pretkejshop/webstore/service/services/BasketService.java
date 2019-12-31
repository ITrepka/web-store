package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Basket;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.BasketRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateBasketDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.BasketDtoMapper;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private BasketDtoMapper basketDtoMapper;
    @Autowired
    private UserRepository userRepository;

    public List<BasketDto> getAllBaskets() {
        return basketRepository.findAll().stream()
                .map(basket -> basketDtoMapper.toDto(basket))
                .collect(Collectors.toList());
    }

    @Transactional
    public BasketDto getBasketById(int id) throws NotFoundException {
        return basketRepository.findById(id)
                .map(basket -> basketDtoMapper.toDto(basket))
                .orElseThrow(() -> new NotFoundException("Not found basket with id = " + id));
    }

    @Transactional
    public BasketDto addNewBasket(CreateUpdateBasketDto createBasketDto) throws InvalidDataException, NotFoundException {
        Basket basket = basketDtoMapper.toModel(createBasketDto);
        basket.setCreatedAt(OffsetDateTime.now());
        Basket savedBasket = basketRepository.save(basket);
        return basketDtoMapper.toDto(savedBasket);
    }

    @Transactional
    public BasketDto updateBasketById(int id, CreateUpdateBasketDto basketToUpdate) throws InvalidDataException, NotFoundException {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Basket with id = " + id));
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Not Found User with id = " + id));
        basket.setUser(user);
        basket.setUpdatedAt(OffsetDateTime.now());
        Basket savedBasket = basketRepository.save(basket);
        return basketDtoMapper.toDto(savedBasket);
    }

    @Transactional
    public BasketDto deleteBasketById(int id) throws NotFoundException {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found basket with id = " + id));
        basketRepository.deleteById(id);
        return basketDtoMapper.toDto(basket);
    }
}
