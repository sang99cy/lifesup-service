package me.sunrise.service.impl;

import me.sunrise.dto.CommentDTO;
import me.sunrise.entity.CommentEntity;
import me.sunrise.entity.ProductEnity;
import me.sunrise.entity.UserEntity;
import me.sunrise.repository.CommentRepository;
import me.sunrise.repository.ProductInfoRepository;
import me.sunrise.repository.UserRepository;
import me.sunrise.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends BaseService implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Override
    public CommentEntity save(CommentEntity comment) {
        comment.setStatus(1L);
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByProductId(String productId) {
        return commentRepository.getListCommentByProductId(productId);
    }

    @Override
    public List<CommentEntity> getALlComment() {
        List<CommentEntity> comments = commentRepository.findAll();
        comments.stream().filter(e -> e.getStatus() != 0L).collect(Collectors.toList());
        return comments;
    }

    @Override
    public boolean changeStatus(Long commentId, Long status) {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            comment.get().setStatus(status);
            commentRepository.save(comment.get());
            return true;
        }
        return false;
    }

    @Override
    public void deleteCommentId(Long commentId) {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
        }
    }
}
