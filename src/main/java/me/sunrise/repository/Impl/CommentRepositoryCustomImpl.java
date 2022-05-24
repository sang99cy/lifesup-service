package me.sunrise.repository.Impl;

import me.sunrise.dto.CommentDTO;
import me.sunrise.repository.CommentRepositoryCustom;
import me.sunrise.util.DataUtil;
import me.sunrise.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CommentDTO> getListCommentByProductId(String productId) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT\n" +
                "\tcm.id as id,\n" +
                "\tcm.comment as comment,\n" +
                "\tu.name as username,\n" +
                "\tu.avatar as avatar, \n" +
                "\tcm.create_time as createTime\n" +
                "FROM\n" +
                "\tcomment cm\n" +
                "\tleft join products p  on cm.product_id = p.product_id\n" +
                "\tleft join users u on cm.user_id = u.id\n" +
                "\twhere 1 = 1 ");
        if (Objects.nonNull(productId)) {
            sql.append("and cm.product_id = '" + productId + "' ");
        }
        sql.append("and cm.status = 0 ");
        sql.append("ORDER BY id DESC; ");
        Query query = em.createNativeQuery(sql.toString(), "sqlComments");
        JpaUtil.setQueryParams(query, params);
        return query.getResultList();
    }
}
