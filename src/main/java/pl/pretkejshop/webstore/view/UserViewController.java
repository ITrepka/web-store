package pl.pretkejshop.webstore.view;

import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.UserDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.UserService;

@Controller
public class UserViewController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ModelAndView displayMyAccountPage(@PathVariable Integer id) throws NotFoundException {
        UserDto user = userService.getUserById(id);
        ModelAndView mv = new ModelAndView("my-account");
        mv.addObject("user", user);
        return mv;
    }
}
