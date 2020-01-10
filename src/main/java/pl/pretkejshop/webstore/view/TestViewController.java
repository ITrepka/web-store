package pl.pretkejshop.webstore.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestViewController {
    @GetMapping("/test")
    public String displayTest() {
        return "test";
    }
}
