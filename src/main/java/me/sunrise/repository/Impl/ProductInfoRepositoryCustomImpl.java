package me.sunrise.repository.Impl;

import me.sunrise.dto.ProductDTO;
import me.sunrise.repository.ProductInfoRepositoryCustom;
import me.sunrise.util.DataUtil;
import me.sunrise.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ProductInfoRepositoryCustomImpl implements ProductInfoRepositoryCustom {


    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductDTO> doSearchProduct(ProductDTO dto) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("select ");
        sql.append("p.product_id productId,\n" +
                "\tp.product_name productName,\n" +
                "\tp.product_price productPrice,\n" +
                "\tp.product_stock productStock,\n" +
                "\tp.product_description productDescription,\n" +
                "\tp.product_icon productIcon,\n" +
                "\tp.product_status productStatus,\n" +
                "\tp.category_type categoryType,\n" +
                "\tp.create_time createTime, \n" +
                "\tp.update_time updateTime ");
        sql.append("FROM products p \n");
        sql.append("WHERE 1 = 1 \n");

        if (Objects.nonNull(dto.getProductStatus())) {
            sql.append("and p.product_status= :status \n");
            params.put("status", dto.getProductStatus());
        }

        if (Objects.nonNull(dto.getProductName())) {
            sql.append("and lower(p.product_name) like :productName ESCAPE '&' \n");
            params.put("productName", DataUtil.makeLikeParam(dto.getProductName()));
        }

        if (Objects.nonNull(dto.getProductPrice())) {
            sql.append("and p.product_name =:productPrice \n");
            params.put("productPrice", DataUtil.makeLikeParam(dto.getProductName()));
        }

        if (Objects.nonNull(dto.getCreateTime())) {
            sql.append("and om.create_time >= STR_TO_DATE('" + dto.getCreateTime() + "','%Y-%m-%d') "); //2022-05-18 16:42:16
        }
//        if (Objects.nonNull(dto.getCreateTime())) {
//            sql.append("and om.create_time <= STR_TO_DATE('" + dto.getCreateTime() + "','%Y-%m-%d') "); //2022-05-19 13:08:19
//        }
        sql.append("Order by p.create_time DESC ");
        Query query = em.createNativeQuery(sql.toString(), "sqlDoSearchProduct");
        JpaUtil.setQueryParams(query, params);
        return query.getResultList();
    }
}
