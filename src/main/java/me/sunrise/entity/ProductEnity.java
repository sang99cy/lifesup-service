package me.sunrise.entity;

import lombok.Data;
import me.sunrise.dto.ProductDTO;
import me.sunrise.dto.StatisticsDTO;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;;

@Entity
@Data
@DynamicUpdate
@Table(name = "products")
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "sqlDoSearchProduct", classes = {
                @ConstructorResult(targetClass = ProductDTO.class,
                        columns = {
                                @ColumnResult(name = "productId", type = String.class),
                                @ColumnResult(name = "productName", type = String.class),
                                @ColumnResult(name = "productPrice", type = BigDecimal.class),
                                @ColumnResult(name = "productStock", type = Integer.class),
                                @ColumnResult(name = "productDescription", type = String.class),
                                @ColumnResult(name = "productIcon", type = String.class),
                                @ColumnResult(name = "productStatus", type = Integer.class),
                                @ColumnResult(name = "categoryType", type = Integer.class),
                                @ColumnResult(name = "createTime", type = LocalDateTime.class),
                                @ColumnResult(name = "updateTime", type = LocalDateTime.class),
                        })
        }),
})
public class ProductEnity implements Serializable {
    @Id
    private String productId;


    private String productName;


    private BigDecimal productPrice;


    @Min(0)
    private Integer productStock;


    private String productDescription;

    private String productIcon;

    @ColumnDefault("0")
    private Integer productStatus;

    @ColumnDefault("0")
    private Integer categoryType;

    @CreationTimestamp
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;

    public ProductEnity() {
    }
}
