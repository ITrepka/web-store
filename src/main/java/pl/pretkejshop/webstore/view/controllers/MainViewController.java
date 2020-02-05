package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import java.util.List;

@Controller
public class MainViewController {
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public ModelAndView displayHomepage() throws NotFoundException {
        List<ProductViewDto> allAvaibleProducts = shopViewService.getAllAvaibleProducts();
        List<ProductViewDto> shirts = shopViewService.searchProductByText("shirt", allAvaibleProducts);
        List<ProductViewDto> shoes = shopViewService.searchProductByText("shoes", allAvaibleProducts);
        List<ProductViewDto> jeans = shopViewService.searchProductByText("jeans", allAvaibleProducts);
        ModelAndView mv = new ModelAndView("homepage");
        mv.addObject("shirts", shirts);
        mv.addObject("shoes", shoes);
        mv.addObject("jeans", jeans);

        return mv;
    }
}
