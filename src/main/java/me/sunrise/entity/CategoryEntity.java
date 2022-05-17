package me.sunrise.entity;

import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name = "category")
public class CategoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;

    private Integer status;

    public CategoryEntity() {
    }

    public CategoryEntity(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

}
