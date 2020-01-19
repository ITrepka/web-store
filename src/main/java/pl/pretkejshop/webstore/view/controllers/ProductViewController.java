package pl.pretkejshop.webstore.view.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.dto.CreateUpdateAdDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateCommentDto;
import pl.pretkejshop.webstore.service.dto.CreateUpdateRateDto;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;
import pl.pretkejshop.webstore.view.service.dto.ProductViewDto;
import pl.pretkejshop.webstore.view.service.services.ShopViewService;

import java.util.List;

@Controller
public class ProductViewController {
    @Autowired
    private ShopViewService shopViewService;

    @GetMapping("/product/{id}")
    public ModelAndView displayProductView(@PathVariable Integer id) throws NotFoundException {
        ModelAndView mv = new ModelAndView("product");
        ProductViewDto product = shopViewService.getProductById(id);
        List<ProductViewDto> relatedProducts = shopViewService.getRelatedProducts(product);
        mv.addObject("product", product);
        mv.addObject("relatedProducts", relatedProducts);
        mv.addObject("comment", new CreateUpdateCommentDto());
        return mv;
    }
}
