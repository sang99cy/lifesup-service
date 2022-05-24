package me.sunrise.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.sunrise.dto.CommentDTO;
import me.sunrise.dto.StatisticsDTO;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * bình luận sản phẩm
 */
@Entity
@Data
@Table(name = "comment")
@NoArgsConstructor
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "sqlComments", classes = {
                @ConstructorResult(targetClass = CommentDTO.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "comment", type = String.class),
                                @ColumnResult(name = "username", type = String.class),
                                @ColumnResult(name = "avatar", type = String.class),
                                @ColumnResult(name = "createTime", type = Date.class),
                        })
        }),
})
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @NotNull
    private String comment;

    @NotNull
    private String productId;

    @Column(name = "user_id")
    private Long userId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private Long status;

}
