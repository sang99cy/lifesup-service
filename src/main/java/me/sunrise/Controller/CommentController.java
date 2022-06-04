package me.sunrise.Controller;

import me.sunrise.dto.CommentDTO;
import me.sunrise.entity.CommentEntity;
import me.sunrise.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    //phuong thuc cho client dang ky

    @RequestMapping(value = "/subscribe",consumes = MediaType.ALL_VALUE)
    public SseEmitter subcriber(){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("init"));
        }catch (IOException exception){
            exception.printStackTrace();
        }
        emitters.add(sseEmitter);
        sseEmitter.onCompletion(()-> emitters.remove(sseEmitter));
        return sseEmitter;
    }

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
        Boolean statusComment = commentService.changeStatus(commentId, status);
        for(SseEmitter emitter : emitters){
            try {
                emitter.send(SseEmitter.event().name("status").data(statusComment));
            }catch (IOException exception){
                emitters.remove(emitter);
            }
        }
        return statusComment;
    }

    @DeleteMapping
    public void deleteComment(@RequestParam Long commentId) {
        commentService.deleteCommentId(commentId);
    }
}
