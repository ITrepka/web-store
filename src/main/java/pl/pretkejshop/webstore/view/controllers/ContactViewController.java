package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.config.AuthenticationSystem;
import pl.pretkejshop.webstore.service.dto.CreateUpdateMessageDto;
import pl.pretkejshop.webstore.service.dto.MessageDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.view.service.dto.UserViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

@Controller
public class ContactViewController {
    @Autowired
    private ShopViewService shopViewService;
    @Autowired
    private AuthenticationSystem authentication;

    @GetMapping("/contact")
    public ModelAndView displayContactFormView() throws NotFoundException {
        ModelAndView mv = new ModelAndView("contact");
        if (authentication.isLogged()) {
            String email = authentication.getName();
            UserViewDto user = shopViewService.getUserViewByLogin(email);
            mv.addObject("userId", user.getUserId());
        }
        CreateUpdateMessageDto message = new CreateUpdateMessageDto();
        mv.addObject("message", message);
        return mv;
    }

    @PostMapping("/contact")
    public ModelAndView addMessage(@ModelAttribute CreateUpdateMessageDto message) throws NotFoundException {
        if (!authentication.isLogged()) {
            return new ModelAndView("login");
        }
        ModelAndView mv = new ModelAndView("contact");
        String email = authentication.getName();
        UserViewDto user = shopViewService.getUserViewByLogin(email);
        mv.addObject("userId", user.getUserId());
        MessageDto messageDto = shopViewService.sendMessage(message);
        CreateUpdateMessageDto message1 = new CreateUpdateMessageDto();
        mv.addObject("message", message1);
        if (messageDto != null) {
            mv.addObject("messageSent", true);
        }
        return mv;
    }
}
