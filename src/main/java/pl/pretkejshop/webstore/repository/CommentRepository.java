package pl.pretkejshop.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pretkejshop.webstore.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
