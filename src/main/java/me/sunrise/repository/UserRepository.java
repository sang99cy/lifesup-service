package me.sunrise.repository;
import me.sunrise.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    UserEntity findById(Long id);
    Collection<UserEntity> findAllByRole(String role);
    Page<UserEntity> findAll(Pageable pageable);

}
