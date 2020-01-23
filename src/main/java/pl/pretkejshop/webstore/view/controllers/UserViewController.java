package pl.pretkejshop.webstore.view.controllers;

import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;
import pl.pretkejshop.webstore.view.service.dto.UserViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/my-account")
public class UserViewController {
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping
    public ModelAndView displayMyAccountPage(Authentication authentication) throws NotFoundException {
        String username = authentication.getName();
        UserViewDto user = shopViewService.getUserViewByLogin(username);
        ModelAndView mv = new ModelAndView("my-account");
        mv.addObject("user", user);
        return mv;
    }

    @PostMapping("/{id}")
    public ModelAndView changeUserAccountData(@ModelAttribute(name = "user") UserViewDto user,
                                              @PathVariable Integer id) throws NotFoundException, InvalidDataException {
        shopViewService.updateUserView(id, user);
        ModelAndView mv = new ModelAndView("redirect:/my-account");
        return mv;
    }
}
