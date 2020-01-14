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

import javax.servlet.http.HttpSession;

@Controller
public class UserViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ModelAndView displayMyAccountPage(Authentication authentication) throws NotFoundException {
        String username = authentication.getName();
        UserDto user = userService.getUserByLogin(username);
        ModelAndView mv = new ModelAndView("my-account");
        mv.addObject("user", user);
        return mv;
    }
}
