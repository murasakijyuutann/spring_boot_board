package mjyuu.spring_boot_board.controller;

import lombok.RequiredArgsConstructor;
import mjyuu.spring_boot_board.dto.PostDTO;
import mjyuu.spring_boot_board.dto.RegisterDTO;
import mjyuu.spring_boot_board.entity.Post;
import mjyuu.spring_boot_board.repository.PostRepository;
import mjyuu.spring_boot_board.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 1. Homepage - list all posts
    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "index";
    }

    // 2. Show login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 3. Show register page
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // 4. Process registration form
    @PostMapping("/register-process")
    public String processRegister(RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return "redirect:/register?error=email";
        }
        var user = new mjyuu.spring_boot_board.entity.User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        userRepository.save(user);
        return "redirect:/login?registered";
    }

    // 5. Show post creation form
    @GetMapping("/posts/create")
    public String createPostForm() {
        return "post-create";
    }

    // 6. Process post creation form
    @PostMapping("/posts/create")
    public String processCreatePost(PostDTO dto, Principal principal) {
        if (principal == null) {
            return "redirect:/login?unauthorized";
        }
        var user = userRepository.findByEmail(principal.getName()).orElseThrow();
        var post = new Post();
        post.setAuthor(user);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        postRepository.save(post);
        return "redirect:/";
    }

    // 7. View post details
    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model, Principal principal) {
        var post = postRepository.findById(id).orElseThrow();
        model.addAttribute("post", post);

        // Check if current user is the author
        boolean ownPost = principal != null && post.getAuthor().getEmail().equals(principal.getName());
        model.addAttribute("ownPost", ownPost);
        return "post-detail";
    }

    // 8. Delete a post (from Thymeleaf)
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, Principal principal) {
        var post = postRepository.findById(id).orElseThrow();
        if (principal == null || !post.getAuthor().getEmail().equals(principal.getName())) {
            return "redirect:/?unauthorized";
        }
        postRepository.delete(post);
        return "redirect:/";
    }
}
