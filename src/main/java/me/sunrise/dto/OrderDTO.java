package me.sunrise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.sunrise.entity.ProductInOrderEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String buyerEmail;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private BigDecimal orderAmount;
    private Integer orderStatus;
    private Date createTime;
    private Date updateTime;
    private Long userId;
    private Long payment;/*hình thức thanh toán 0 or 1*/
    private String comment;/*chú thích */
    List<DetailOrderDTO> detailOrders;


    public OrderDTO(Long orderId) {
        this.orderId = orderId;
    }
}
