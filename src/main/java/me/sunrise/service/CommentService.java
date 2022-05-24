package me.sunrise.service;

import me.sunrise.dto.CommentDTO;
import me.sunrise.entity.CommentEntity;

import java.util.List;

public interface CommentService {
    CommentEntity save(CommentEntity comment);

    List<CommentDTO> getCommentsByProductId(String productId);

    List<CommentEntity> getALlComment();

    boolean changeStatus(Long commentId, Long status);

    void deleteCommentId(Long commentId);
}
