package me.sunrise.vo.response;

import lombok.Data;

@Data
public class JwtResponse {
    private Long userId;
    private String token;
    private String type = "Bearer";
    private String account;
    private String name;
    private String address;
    private String phone;
    private String role;

    public JwtResponse(String token, String account, String name, String role, String address, String phone) {
        this.account = account;
        this.name = name;
        this.token = token;
        this.role = role;
        this.address = address;
        this.phone = phone;
    }

    public JwtResponse(Long userId, String token, String account, String name, String role, String address, String phone) {
        this.userId = userId;
        this.account = account;
        this.name = name;
        this.token = token;
        this.role = role;
        this.address = address;
        this.phone = phone;
    }
}
