package me.sunrise.service.impl;

import me.sunrise.entity.CommentEntity;
import me.sunrise.repository.CommentRepository;
import me.sunrise.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public CommentEntity save(CommentEntity comment) {
        return commentRepository.save(comment);
    }
}
