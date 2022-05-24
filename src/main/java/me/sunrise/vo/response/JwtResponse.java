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
    private String avatar;

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

    public JwtResponse(Long userId, String token, String account, String name, String role, String address, String phone,String avatar) {
        this.userId = userId;
        this.account = account;
        this.name = name;
        this.token = token;
        this.role = role;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
    }

    public JwtResponse(Long userId, String token, String type, String account, String name, String address, String phone, String role, String avatar) {
        this.userId = userId;
        this.token = token;
        this.type = type;
        this.account = account;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.avatar = avatar;
    }
}
