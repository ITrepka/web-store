package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.Sex;
import pl.pretkejshop.webstore.service.dto.CreateUpdatePersonalDataDto;
import pl.pretkejshop.webstore.service.dto.CreateUserDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;

import javax.transaction.Transactional;

@Controller
public class RegisterViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ModelAndView displayRegisterForm() {
        ModelAndView mv = new ModelAndView("register");
        CreateUserDto user = new CreateUserDto();
        mv.addObject("user", user);
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@ModelAttribute(name = "user") CreateUserDto user) throws AlreadyExistsException, InvalidDataException, NotFoundException {
        userService.addNewUser(user);
        ModelAndView mv = new ModelAndView("redirect:/login");
        return mv;
    }

}
