package pl.pretkejshop.webstore.view.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.service.dto.DeliveryTypeDto;
import pl.pretkejshop.webstore.service.services.DeliveryTypeService;

import java.util.List;

@Service
public class OrderViewService {
    @Autowired
    private DeliveryTypeService deliveryTypeService;

    public List<DeliveryTypeDto> getDeliveryTypes() {
        return deliveryTypeService.getAllDeliveryTypes();
    }
}
