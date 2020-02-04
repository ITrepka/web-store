package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pretkejshop.webstore.service.services.RelationsTestService;

@RestController
public class RelationsController {
    @Autowired
    private RelationsTestService relationsTestService;

    @GetMapping("testOrderShippingDetails")
    private String testOrderShippingDetails() {
        return relationsTestService.testOrderShippingDetailsRelation();
    }
}
