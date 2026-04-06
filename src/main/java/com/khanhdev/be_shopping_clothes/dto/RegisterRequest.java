package com.khanhdev.be_shopping_clothes.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String full_name;   // khớp với JSON key của FE
    private String email;
    private String password;    // plain-text, backend sẽ hash
    private String phone;       // nullable
}
