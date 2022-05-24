package me.sunrise.Controller;

import me.sunrise.dto.CommentDTO;
import me.sunrise.entity.CommentEntity;
import me.sunrise.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CommentEntity comment) {
        try {
            comment.setStatus(1L);
            CommentEntity returnedComment = commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> edit(@RequestBody CommentEntity comment) {
        CommentEntity returnedComment;
        try {
            if (comment != null) {
                returnedComment = commentService.save(comment);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<CommentDTO> listCommentByUserId(@RequestParam String productId) {
        return commentService.getCommentsByProductId(productId);
    }

    @GetMapping("/all")
    public List<CommentEntity> lisAllComment() {
        return commentService.getALlComment();
    }

    @PostMapping("/status")
    public boolean changeStatus(@RequestParam Long commentId, @RequestParam Long status) {
        return commentService.changeStatus(commentId, status);
    }

    @DeleteMapping
    public void deleteComment(@RequestParam Long commentId) {
        commentService.deleteCommentId(commentId);
    }

}
