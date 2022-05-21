package me.sunrise.repository.Impl;

import me.sunrise.dto.OrderDTO;
import me.sunrise.dto.StatisticsDTO;
import me.sunrise.dto.TimeDto;
import me.sunrise.repository.StatisticsRepositoryCustom;
import me.sunrise.util.DataUtil;
import me.sunrise.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StatisticsRepositoryCustomImpl implements StatisticsRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StatisticsDTO> thongkeDoanhThu(TimeDto dto) {
        HashMap<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT \n" +
                "SUM(om.order_amount) doanhThu, \n" +
                "  po.product_name tenSanPham,\n" +
                "  po.count soLuong\n" +
                "from \n" +
                "order_main om\n" +
                "inner join products__order po " +
                "on om.order_id = po.order_id  \n" +
                "WHERE 1 = 1 \n");

        if (Objects.nonNull(dto.getProductName())) {
            sql.append("and lower(po.product_name) like :productName ESCAPE '&' \n");
            params.put("productName", DataUtil.makeLikeParam(dto.getProductName()));
        }

        if (Objects.nonNull(dto.getCreateForm())) {
            sql.append("and om.create_time >= STR_TO_DATE('" + dto.getCreateForm() + "','%Y-%m-%d') "); //2022-05-18 16:42:16
        }
        if (Objects.nonNull(dto.getCreateTo())) {
            sql.append("and om.create_time <= STR_TO_DATE('" + dto.getCreateTo() + "','%Y-%m-%d') "); //2022-05-19 13:08:19
        }
        sql.append("GROUP BY tenSanPham,soLuong; ");
        Query query = em.createNativeQuery(sql.toString(), "sqlStatistics");
        JpaUtil.setQueryParams(query, params);
        return query.getResultList();

    }

    @Override
    public Long thongkeSoluongDonHangHomnay() {
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT\n" +
                "\tcount(om.order_id) soLuong \n" +
                "FROM\n" +
                "\torder_main om\n" +
                "WHERE\n" +
                "\tom.create_time between concat(curdate(), ' ', '00:00:00') AND concat(curdate(), ' ', '23:59:59'); ");
        List<StatisticsDTO> query = em.createNativeQuery(sql.toString(), "countOrderCunrrentDate").getResultList();
        Long count = query.get(0).getSoLuong();
        return count;
    }

    @Override
    public Long thongkeSoluongDonHangThangnay() {
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT\n" +
                "\tcount(om.order_id) soLuong\n" +
                "FROM\n" +
                "\torder_main om\n" +
                "WHERE\n" +
                "\tMONTH(om.create_time) = MONTH(CURRENT_DATE()); ");
        List<StatisticsDTO> query = em.createNativeQuery(sql.toString(), "countOrderCunrrentDate").getResultList();
        Long count = query.get(0).getSoLuong();
        return count;
    }

    @Override
    public StatisticsDTO thongkeDoanhThuHomnay() {
        StringBuilder sql = new StringBuilder("");
        sql.append(" SELECT\n" +
                "\tSUM(om.order_amount) doanhThu,\n" +
                "\tcount(om.order_id) soLuong\n" +
                "FROM\n" +
                "\torder_main om\n" +
                "WHERE\n" +
                "\tom.create_time between concat(curdate(), ' ', '00:00:00') AND concat(curdate(), ' ', '23:59:59'); ");
        StatisticsDTO query = (StatisticsDTO) em.createNativeQuery(sql.toString(), "sumSalesCunrrentDate").getSingleResult();
        return query;
    }

    @Override
    public StatisticsDTO thongkeDoanhThuThangnay() {
        StringBuilder sql = new StringBuilder("");
        sql.append(" SELECT\n" +
                "\tSUM(om.order_amount) doanhThu,\n" +
                "\tcount(om.order_id) soLuong\n" +
                "FROM\n" +
                "\torder_main om\n" +
                "WHERE\n" +
                "\tMONTH(om.create_time) = MONTH(CURRENT_DATE()); ");
        StatisticsDTO query = (StatisticsDTO) em.createNativeQuery(sql.toString(), "sumSalesCunrrentDate").getSingleResult();
        return query;
    }


    @Override
    public StatisticsDTO thongkeDoanhThuTheoQuy(Long type) {
        StatisticsDTO query = null;
        StringBuilder sql = new StringBuilder("");
        if (type == 0) {
            sql.append(" SELECT \n" +
                    "\t(CASE\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 1 and 3  THEN 1\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 4 and 6 THEN 2\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 7 and 9 then 3\n" +
                    "\t    ELSE 4\n" +
                    "\tEND) AS quy, SUM(om.order_amount) AS doanhThu\n" +
                    "\tFROM order_main om\n" +
                    "\tWHERE om.order_status = 3\n" +
                    "\tGROUP BY((CASE\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 1 and 3  THEN 1\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 4 and 6 THEN 2\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 7 and 9 then 3\n" +
                    "\t    ELSE 4\n" +
                    "\tEND))");
            query = (StatisticsDTO) em.createNativeQuery(sql.toString(), "sumSalesByQuater").getSingleResult();
        }
        if (type == 1) {
            sql.append(" SELECT \n" +
                    "\t(CASE\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 1 and 3  THEN 1\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 4 and 6 THEN 2\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 7 and 9 then 3\n" +
                    "\t    ELSE 4\n" +
                    "\tEND) AS quy, COUNT(om.order_amount) AS soLuong\n" +
                    "\tFROM order_main om\n" +
                    "\tWHERE om.order_status = 3\n" +
                    "\tGROUP BY((CASE\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 1 and 3  THEN 1\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 4 and 6 THEN 2\n" +
                    "\t    WHEN MONTH(om.create_time) BETWEEN 7 and 9 then 3\n" +
                    "\t    ELSE 4\n" +
                    "\tEND))");
            query = (StatisticsDTO) em.createNativeQuery(sql.toString(), "countSalesByQuater").getSingleResult();
        }
        return query;
    }

    @Override
    public List<StatisticsDTO> thongKeDoanhthuHangNgay(TimeDto timeDto) {
        List<StatisticsDTO> statistics;
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT SUM(om.order_amount) doanhThu,\n" +
                "\tom.create_time ngay\n" +
                "\tfrom \n" +
                "\torder_main om\n" +
                "\twhere  1 = 1 ");
        if (Objects.nonNull(timeDto.getCreateForm())) {
            sql.append("and om.create_time >= STR_TO_DATE('" + timeDto.getCreateForm() + "','%Y-%m-%d %H:%i:%s') ");
        }
        if (Objects.nonNull(timeDto.getCreateTo())) {
            sql.append("and om.create_time <= STR_TO_DATE('" + timeDto.getCreateTo() + "','%Y-%m-%d %H:%i:%s') ");
        }
        sql.append("group by ngay; ");
        statistics = em.createNativeQuery(sql.toString(), "sumSalesByCreateFormTo").getResultList();
        return statistics;
    }
}
