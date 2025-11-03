package mjyuu.spring_boot_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}
