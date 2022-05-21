package me.sunrise.service;

import me.sunrise.dto.OrderDTO;
import me.sunrise.dto.Response.StatisticsResponse;
import me.sunrise.dto.StatisticsDTO;
import me.sunrise.dto.TimeDto;

import java.util.List;

public interface StatisticsService {

    StatisticsResponse thongkeDoanhThu(TimeDto statistics);

    Long thongkeSoluongDonHangHomnay();

    Long thongkeSoluongDonHangThangnay();

    StatisticsDTO thongkeDoanhThuHomnay();

    StatisticsDTO thongkeDoanhThuThangnay();

    StatisticsDTO thongkeDoanhThuTheoQuy(Long type);

    List<StatisticsDTO> thongkeDoanhthuHangNgay(TimeDto timeDto);
}
