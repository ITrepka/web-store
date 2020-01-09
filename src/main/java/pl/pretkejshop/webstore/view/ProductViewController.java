package pl.pretkejshop.webstore.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.service.dto.ProductDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.ProductService;

@Controller
public class ProductViewController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product/{id}")
    public ModelAndView displayProductView(@PathVariable Integer id) throws NotFoundException {
        ModelAndView mv = new ModelAndView("product");
        ProductDto product = productService.getProductById(id);
        mv.addObject("product", product);
        return mv;
    }
}
