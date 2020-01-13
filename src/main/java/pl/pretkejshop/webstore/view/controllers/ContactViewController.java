package pl.pretkejshop.webstore.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactViewController {
    @GetMapping("/contact")
    public ModelAndView displayContactFormView() {
        ModelAndView mv = new ModelAndView("contact");
        return mv;
    }
}
