package me.sunrise.repository;
import me.sunrise.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Collection<UserEntity> findAllByRole(String role);
    Page<UserEntity> findAll(Pageable pageable);

}
