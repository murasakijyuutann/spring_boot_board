package mjyuu.spring_boot_board.controller;

import lombok.RequiredArgsConstructor;
import mjyuu.spring_boot_board.entity.Post;
import mjyuu.spring_boot_board.repository.PostRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final PostRepository postRepository;

    @GetMapping("/admin")
    public String dashboard(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "admin";
    }

    @PostMapping("/admin/posts/{id}/delete")
    public String deletePostAsAdmin(@PathVariable @org.springframework.lang.NonNull Long id) {
        postRepository.deleteById(id);
        return "redirect:/admin";
    }
}
