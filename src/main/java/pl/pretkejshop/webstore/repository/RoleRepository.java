package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
