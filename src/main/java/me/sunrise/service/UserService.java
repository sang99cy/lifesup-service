package me.sunrise.service;

import me.sunrise.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface UserService {
    UserEntity findOne(String email);

    UserEntity findUserById(Long id);

    Collection<UserEntity> findByRole(String role);

    UserEntity save(UserEntity user);

    UserEntity update(UserEntity user);

    UserEntity updateUser(UserEntity user);

    void delete(Long id);

    Page<UserEntity> findAll(Pageable pageable);

    List<UserEntity> findAll();

    UserEntity updatePassword(Long userId,String newPassword,String oldPassword);
}
