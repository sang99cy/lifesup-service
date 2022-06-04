//package me.sunrise.Controller;
//
//import me.sunrise.service.CommentService;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@RestController
//public class SseController {
//
//    @Autowired
//    CommentService commentService;
//
//    public static List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
//    //phuong thuc cho client dang ky
//
//    @CrossOrigin
//    @RequestMapping(value = "/subscribe",consumes = MediaType.ALL_VALUE)
//    public SseEmitter subcriber(){
//        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
//        try {
//            sseEmitter.send(SseEmitter.event().name("init"));
//        }catch (IOException exception){
//            exception.printStackTrace();
//        }
//        emitters.add(sseEmitter);
//        sseEmitter.onCompletion(()-> emitters.remove(sseEmitter));
//        return sseEmitter;
//    }
//
//    @PostMapping(value = "dispatchEvent")
//    public void dispatchEventToclients(@RequestParam String title,@RequestParam String text){
//        String eventFormatted = new JSONObject()
//                .put("title",title)
//                .put("text",text).toString();
//        for(SseEmitter emitter : emitters){
//            try {
//                emitter.send(SseEmitter.event().name("message").data(eventFormatted));
//            }catch (IOException exception){
//                emitters.remove(emitter);
//            }
//        }
//    }
//
//    //phương thức duyệt bình luận thì bắn 1 sự kiện
//    @PostMapping("/status")
//    public boolean changeStatus(@RequestParam Long commentId, @RequestParam Long status) {
//        Boolean statusComment = commentService.changeStatus(commentId, status);
//        for(SseEmitter emitter : emitters){
//            try {
//                emitter.send(SseEmitter.event().name("status").data(statusComment));
//            }catch (IOException exception){
//                emitters.remove(emitter);
//            }
//        }
//        return statusComment;
//    }
//}
