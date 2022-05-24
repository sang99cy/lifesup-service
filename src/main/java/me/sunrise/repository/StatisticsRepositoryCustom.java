package me.sunrise.repository;

import me.sunrise.dto.OrderDTO;
import me.sunrise.dto.StatisticsDTO;
import me.sunrise.dto.TimeDto;

import java.util.List;

public interface StatisticsRepositoryCustom {

    List<StatisticsDTO> thongkeDoanhThu(TimeDto statistics);

    Long thongkeSoluongDonHangHomnay();

    Long thongkeSoluongDonHangThangnay();

    StatisticsDTO thongkeDoanhThuHomnay();

    StatisticsDTO thongkeDoanhThuThangnay();

    List<StatisticsDTO> thongkeDoanhThuTheoQuy(Long type);

    List<StatisticsDTO> thongKeDoanhthuHangNgay(TimeDto timeDto);
}
