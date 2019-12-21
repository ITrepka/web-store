package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}
