package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.ProductCopy;

@Repository
public interface ProductCopyRepository extends JpaRepository<ProductCopy, Long> {
}
