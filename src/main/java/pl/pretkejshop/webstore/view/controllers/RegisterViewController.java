package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
        CreateUpdatePersonalDataDto personalData = new CreateUpdatePersonalDataDto();
        mv.addObject("personalData",personalData);
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@RequestParam String login, @RequestParam String password,
                                        @ModelAttribute(name = "personalData") CreateUpdatePersonalDataDto personalData) throws AlreadyExistsException, InvalidDataException, NotFoundException {
        CreateUserDto createUserDto = new CreateUserDto(personalData, login, password);
        userService.addNewUser(createUserDto);
        ModelAndView mv = new ModelAndView("redirect:/login");
        return mv;
    }

}
