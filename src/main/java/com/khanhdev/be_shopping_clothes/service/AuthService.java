package com.khanhdev.be_shopping_clothes.service;

import com.khanhdev.be_shopping_clothes.dto.AuthResponse;
import com.khanhdev.be_shopping_clothes.dto.LoginRequest;
import com.khanhdev.be_shopping_clothes.dto.RegisterRequest;
import com.khanhdev.be_shopping_clothes.entity.User;
import com.khanhdev.be_shopping_clothes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Đăng ký tài khoản mới
     * - Kiểm tra email đã tồn tại chưa
     * - Hash mật khẩu plain-text bằng BCrypt
     * - Lưu user với role mặc định 'customer'
     */
    public AuthResponse register(RegisterRequest request) {
        // Kiểm tra email đã tồn tại
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email này đã được sử dụng. Vui lòng dùng email khác."
            );
        }

        // Hash mật khẩu plain-text bằng BCrypt
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Tạo user mới
        User user = User.builder()
                .fullName(request.getFull_name().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(hashedPassword)
                .phone(request.getPhone() != null ? request.getPhone().trim() : null)
                .role(User.Role.customer)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        return AuthResponse.builder()
                .message("Đăng ký thành công!")
                .user(mapToUserInfo(savedUser))
                .build();
    }

    /**
     * Đăng nhập
     * - Tìm user theo email
     * - Dùng BCryptPasswordEncoder.matches() so sánh plain password với hash trong DB
     */
    public AuthResponse login(LoginRequest request) {
        // Tìm user theo email
        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email hoặc mật khẩu không đúng."
                ));

        // Kiểm tra tài khoản có bị khóa không
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Tài khoản của bạn đã bị vô hiệu hóa. Vui lòng liên hệ hỗ trợ."
            );
        }

        // So sánh mật khẩu plain-text với hash trong DB
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Email hoặc mật khẩu không đúng."
            );
        }

        return AuthResponse.builder()
                .message("Đăng nhập thành công!")
                .user(mapToUserInfo(user))
                .build();
    }

    /**
     * Map User entity sang UserInfo DTO (không bao giờ trả password)
     */
    private AuthResponse.UserInfo mapToUserInfo(User user) {
        return AuthResponse.UserInfo.builder()
                .id(user.getId())
                .full_name(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().name() : "customer")
                .avatar_url(user.getAvatarUrl())
                .phone(user.getPhone())
                .build();
    }
}
