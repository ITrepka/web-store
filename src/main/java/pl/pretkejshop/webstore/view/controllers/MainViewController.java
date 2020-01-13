package pl.pretkejshop.webstore.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainViewController {
    @GetMapping({"", "/", "/index", "/index.html"})
    public String displayHomepage() {
        return "homepage";
    }
}
