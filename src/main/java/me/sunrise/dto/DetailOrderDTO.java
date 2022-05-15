package me.sunrise.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderDTO {
    private Long id;
    private String productId;
    private String productName;
    private String productDescription;
    private String productIcon;
    private Integer categoryType;
    private BigDecimal productPrice;
    private Integer productStock;
    private Integer count;
    private Long orderId;

}
