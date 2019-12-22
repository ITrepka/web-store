package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.OrderStatus;
import pl.pretkejshop.webstore.repository.OrderStatusRepository;

@Service
public class OrderStatusDtoMapper {
    @Autowired
    private OrderStatusRepository orderStatusRepository;
}
