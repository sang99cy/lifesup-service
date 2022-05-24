package me.sunrise.repository;

import me.sunrise.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CommentRepositoryCustom {

    List<CommentEntity> findAllByProductId(String productId);
}
