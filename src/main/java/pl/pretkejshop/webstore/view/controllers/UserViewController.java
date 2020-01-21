package pl.pretkejshop.webstore.view.controllers;

import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;
import pl.pretkejshop.webstore.view.service.dto.UserViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import javax.servlet.http.HttpSession;

@Controller
public class UserViewController {
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping("/user")
    public ModelAndView displayMyAccountPage(Authentication authentication) throws NotFoundException {
        String username = authentication.getName();
        UserViewDto user = shopViewService.getUserViewByLogin(username);
        ModelAndView mv = new ModelAndView("my-account");
        mv.addObject("user", user);
        return mv;
    }
}
