package com.khanhdev.be_shopping_clothes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String message;

    // Thông tin user trả về cho FE (không bao giờ trả password)
    private UserInfo user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String full_name;   // dùng snake_case để khớp với FE
        private String email;
        private String role;
        private String avatar_url;
        private String phone;
    }
}
