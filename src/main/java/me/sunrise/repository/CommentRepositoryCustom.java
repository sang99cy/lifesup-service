package me.sunrise.repository;

import me.sunrise.dto.CommentDTO;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentDTO> getListCommentByProductId(String productId);
}
