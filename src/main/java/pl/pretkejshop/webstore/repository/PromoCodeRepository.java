package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.PromoCode;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Integer> {
    Optional<PromoCode> findByName(String name);
}
