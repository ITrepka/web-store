package pl.pretkejshop.webstore.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.BasketDto;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.BasketService;
import pl.pretkejshop.webstore.service.services.UserService;
import pl.pretkejshop.webstore.view.service.dto.BasketViewDto;
import pl.pretkejshop.webstore.view.service.mapper.BasketViewDtoMapper;

import javax.servlet.http.HttpSession;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private AuthenticationSystem authenticationSystem;
    @Autowired
    private UserService userService;
    @Autowired
    private BasketService basketService;
    @Autowired
    private BasketViewDtoMapper basketViewDtoMapper;

    @SneakyThrows
    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        String login = authenticationSystem.getName();
        UserDto user = userService.getUserByLogin(login);
        Integer basketId = user.getBasketId();
        BasketDto basketById = basketService.getBasketById(basketId);
        BasketViewDto basketViewDto = basketViewDtoMapper.toViewDto(basketById);
        httpSession.setAttribute("userCart", basketViewDto);
        System.out.println("User Logged In");

    }
}