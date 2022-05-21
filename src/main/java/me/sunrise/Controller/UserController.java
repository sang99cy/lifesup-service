package me.sunrise.Controller;

import me.sunrise.entity.UserEntity;
import me.sunrise.security.JWT.JwtProvider;
import me.sunrise.service.UserService;
import me.sunrise.service.impl.UserServiceImpl;
import me.sunrise.vo.request.LoginForm;
import me.sunrise.vo.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginForm loginForm) {
        // throws Exception if authentication failed

        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()));

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về jwt cho người dùng.
            String jwt = jwtProvider.generate(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Lấy một số thông tin của người dùng khi đăng nhập.
            UserEntity UserEntity = userService.findOne(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(UserEntity.getId(), jwt, UserEntity.getEmail(), UserEntity.getName(), UserEntity.getRole(), UserEntity.getAddress(), UserEntity.getPhone()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity UserEntity) {
        try {
            return ResponseEntity.ok(userService.save(UserEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<UserEntity> update(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(userServiceImpl.update(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<UserEntity> getProfile(@PathVariable("email") String email, Principal principal) {
        if (principal.getName().equals(email)) {
            return ResponseEntity.ok(userService.findOne(email));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/profile/changePassword")
    public ResponseEntity<UserEntity> updatePassword(@RequestParam Long userId, @RequestParam String newPassword, @RequestParam String oldPassword) {
        try {
            return ResponseEntity.ok(userService.updatePassword(userId, newPassword, oldPassword));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getALlUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/userList")
    public Page<UserEntity> userList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size,
                                     Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<UserEntity> userPage;

        userPage = userService.findAll(request);

        return userPage;
    }


    @PutMapping("/edit/UserEntity/{id}")
    public ResponseEntity edit(@PathVariable("id") Long id,
                               @Valid @RequestBody UserEntity UserEntity,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!id.equals(UserEntity.getId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }

        return ResponseEntity.ok(userService.updateUser(UserEntity));
    }

    @DeleteMapping("/delete/UserEntity/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
