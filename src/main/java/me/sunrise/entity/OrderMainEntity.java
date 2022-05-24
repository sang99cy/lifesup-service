package me.sunrise.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.sunrise.dto.OrderDTO;
import me.sunrise.dto.StatisticsDTO;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Hóa đơn sản phẩm*/
@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
@Table(name = "order_main")
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "sqlStatistics", classes = {
                @ConstructorResult(targetClass = StatisticsDTO.class,
                        columns = {
                                @ColumnResult(name = "tenSanPham", type = String.class),
                                @ColumnResult(name = "soLuong", type = Long.class),
                                @ColumnResult(name = "doanhThu", type = Long.class),
                        })
        }),
        @SqlResultSetMapping(name = "countOrderCunrrentDate", classes = {
                @ConstructorResult(targetClass = StatisticsDTO.class,
                        columns = {
                                @ColumnResult(name = "soLuong", type = Long.class),
                        })
        }),
        @SqlResultSetMapping(name = "sumSalesCunrrentDate", classes = {
                @ConstructorResult(targetClass = StatisticsDTO.class,
                        columns = {
                                @ColumnResult(name = "doanhThu", type = Long.class),
                                @ColumnResult(name = "soLuong", type = Long.class),
                        })
        }),
        @SqlResultSetMapping(name = "sumSalesByQuater", classes = {
                @ConstructorResult(targetClass = StatisticsDTO.class,
                        columns = {
                                @ColumnResult(name = "quy", type = Integer.class),
                                @ColumnResult(name = "doanhThu", type = Long.class),
                        })
        }),
        @SqlResultSetMapping(name = "countSalesByQuater", classes = {
                @ConstructorResult(targetClass = StatisticsDTO.class,
                        columns = {
                                @ColumnResult(name = "soLuong", type = Long.class),
                                @ColumnResult(name = "quy", type = Integer.class),
                        })
        }),
        @SqlResultSetMapping(name = "sumSalesByCreateFormTo", classes = {
                @ConstructorResult(targetClass = StatisticsDTO.class,
                        columns = {
                                @ColumnResult(name = "doanhThu", type = Long.class),
                                @ColumnResult(name = "ngay", type = String.class)
                        })
        }),
})
public class OrderMainEntity implements Serializable {
    private static final long serialVersionUID = -3819883511505235030L;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

//    @OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY,
//            mappedBy = "orderMain")
//    private Set<ProductInOrderEntity> products = new HashSet<>();

    @NotEmpty
    private String buyerEmail;

    private String buyerName;

    @NotEmpty
    private String buyerPhone;

    @NotEmpty
    private String buyerAddress;

    @NotNull
    private BigDecimal orderAmount;

    @NotNull
    @ColumnDefault("0")
    private Integer orderStatus;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "payment")
    private Long payment;/*hình thức thanh toán 0 or 1*/

    @Column(name = "comment")
    private String comment;

    public OrderMainEntity(UserEntity buyer) {
        this.buyerEmail = buyer.getEmail();
        this.buyerName = buyer.getName();
        this.buyerPhone = buyer.getPhone();
        this.buyerAddress = buyer.getAddress();
//        this.orderAmount = buyer.getCart().getProducts().stream().map(item -> item.getProductPrice().multiply(new BigDecimal(item.getCount())))
//                .reduce(BigDecimal::add)
//                .orElse(new BigDecimal(0));
        this.orderStatus = 0;

    }
}
