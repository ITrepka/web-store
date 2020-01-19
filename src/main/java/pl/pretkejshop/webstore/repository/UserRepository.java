package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.PersonalData;
import pl.pretkejshop.webstore.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String login);
    Optional<User> findByEmail(String login);
    Optional<User> findByPersonalData(PersonalData personalData);
}
