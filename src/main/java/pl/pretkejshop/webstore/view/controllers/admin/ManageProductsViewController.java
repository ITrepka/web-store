package pl.pretkejshop.webstore.view.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.pretkejshop.webstore.model.Product;
import pl.pretkejshop.webstore.repository.ProductRepository;

import java.util.List;

@Controller
public class ManageProductsViewController {
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/admin/products-table")
    public ModelAndView displayProductsTable() {
        List<Product> products = productRepository.findAll();
        ModelAndView mv = new ModelAndView("admin/products-table");
        mv.addObject("products", products);
        return mv;
    }
}
