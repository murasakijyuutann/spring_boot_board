package mjyuu.spring_boot_board.controller;

import lombok.RequiredArgsConstructor;
import mjyuu.spring_boot_board.dto.UserSummaryDTO;
import mjyuu.spring_boot_board.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<UserSummaryDTO> list() {
        return userRepository.findAll().stream()
                .map(u -> new UserSummaryDTO(
                        u.getId(),
                        u.getEmail(),
                        u.getNickname(),
                        u.getRole() != null ? u.getRole().name() : null,
                        u.getCreatedAt()
                ))
                .toList();
    }
}
