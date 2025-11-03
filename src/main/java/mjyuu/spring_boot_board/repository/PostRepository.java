package mjyuu.spring_boot_board.repository;

import mjyuu.spring_boot_board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
