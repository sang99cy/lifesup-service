package me.sunrise.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String name;
    private String comment;
    private String productId;
    private Long userId;
    private Date createTime;
    private Long status;

    private String username;
    private String avatar;

    public CommentDTO(Long id, String comment, String username, String avatar, Date createTime) {
        this.id = id;
        this.comment = comment;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
    }
}
