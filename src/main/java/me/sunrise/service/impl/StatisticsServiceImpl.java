package me.sunrise.service.impl;

import me.sunrise.dto.OrderDTO;
import me.sunrise.dto.Response.StatisticsResponse;
import me.sunrise.dto.StatisticsDTO;
import me.sunrise.dto.TimeDto;
import me.sunrise.repository.OrderRepository;
import me.sunrise.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public StatisticsResponse thongkeDoanhThu(TimeDto statistics) {
        StatisticsResponse response = new StatisticsResponse();
        List<StatisticsDTO> danhsachDoanhThu = orderRepository.thongkeDoanhThu(statistics);
        Long total = danhsachDoanhThu.stream().mapToLong(dt -> dt.getDoanhThu()).sum();
        response.setStatistics(danhsachDoanhThu);
        response.setTotal(total);
        return response;
    }

    @Override
    public Long thongkeSoluongDonHangHomnay() {
        return orderRepository.thongkeSoluongDonHangHomnay();
    }

    @Override
    public Long thongkeSoluongDonHangThangnay() {
        return orderRepository.thongkeSoluongDonHangThangnay();
    }

    @Override
    public StatisticsDTO thongkeDoanhThuHomnay() {
        return orderRepository.thongkeDoanhThuHomnay();
    }

    @Override
    public StatisticsDTO thongkeDoanhThuThangnay() {
        return orderRepository.thongkeDoanhThuThangnay();
    }

    @Override
    public StatisticsDTO thongkeDoanhThuTheoQuy(Long type) {
        return orderRepository.thongkeDoanhThuTheoQuy(type);
    }

    @Override
    public  List<StatisticsDTO> thongkeDoanhthuHangNgay(TimeDto timeDto) {
        return orderRepository.thongKeDoanhthuHangNgay(timeDto);
    }
}
