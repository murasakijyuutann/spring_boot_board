package mjyuu.spring_boot_board.controller;

import lombok.RequiredArgsConstructor;
import mjyuu.spring_boot_board.dto.LoginDTO;
import mjyuu.spring_boot_board.dto.RegisterDTO;
import mjyuu.spring_boot_board.entity.User;
import mjyuu.spring_boot_board.repository.UserRepository;
import mjyuu.spring_boot_board.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import mjyuu.spring_boot_board.entity.Role;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
}
