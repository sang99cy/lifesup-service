package me.sunrise.Controller;

import me.sunrise.entity.CommentEntity;
import me.sunrise.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/seller/comment/new")
    public ResponseEntity<?> add(@RequestBody CommentEntity comment) {
        try {
            CommentEntity returnedComment = commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
