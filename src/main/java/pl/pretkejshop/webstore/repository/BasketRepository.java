package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
