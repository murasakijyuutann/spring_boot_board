package mjyuu.spring_boot_board.controller;

import lombok.RequiredArgsConstructor;
import mjyuu.spring_boot_board.dto.PostDTO;
import mjyuu.spring_boot_board.entity.Post;
import mjyuu.spring_boot_board.entity.User;
import mjyuu.spring_boot_board.repository.PostRepository;
import mjyuu.spring_boot_board.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping
    public Post create(@RequestBody PostDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
        User author = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setAuthor(author);

        return postRepository.save(post);
    }

    @GetMapping
    public List<Post> list() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post detail(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthor().getEmail().equals(userDetails.getUsername())) {
            throw new RuntimeException("Not authorized to delete this post");
        }

        postRepository.delete(post);
    }
}
