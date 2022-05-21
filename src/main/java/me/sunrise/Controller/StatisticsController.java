package me.sunrise.Controller;

import me.sunrise.dto.OrderDTO;
import me.sunrise.dto.Response.StatisticsResponse;
import me.sunrise.dto.StatisticsDTO;
import me.sunrise.dto.TimeDto;
import me.sunrise.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;
    public Map<String, Object> map;

    /**
     * bang thong ke co creatForm va createTo
     */
    @PostMapping
    public StatisticsResponse thongkeDoanhThu(@RequestBody TimeDto dto) {
        return statisticsService.thongkeDoanhThu(dto);
    }

    /**
     * thống kê số lượng đơn hàng ngày hôm nay
     */
    @GetMapping("/countOrder/currentDate")
    public Map<String, Object> thongkeSoluongDonHangHomnay() {
        Long soluong = statisticsService.thongkeSoluongDonHangHomnay();
        map = new HashMap<>();
        map.put("soluong", soluong);
        return map;
    }

    /**
     * thong ke so luong don hang thang nay
     */
    @GetMapping("/countOrder/currentMonth")
    public Map<String, Object> thongkeSoluongDonHangThangnay() {
        Long soluong = statisticsService.thongkeSoluongDonHangThangnay();
        map = new HashMap<>();
        map.put("soluong", soluong);
        return map;
    }

    /**
     * thong ke doanh thu ngay hien tai
     */
    @GetMapping("/sumOrder/currentDate")
    public StatisticsDTO thongkeDoanhThuHomnay() {
        return statisticsService.thongkeDoanhThuHomnay();
    }

    @GetMapping("/sumOrder/currentMonth")
    public StatisticsDTO thongkeDoanhThuThangnay() {
        return statisticsService.thongkeDoanhThuThangnay();
    }

    /**
     * thống kê doanh thu hàng ngày hiển thị lên chart hàng ngày
     */
    @PostMapping("/sumOrder/createFromTo")
    public List<StatisticsDTO> thongkeDoanhThuHangNgay(TimeDto timeDto) {
        return statisticsService.thongkeDoanhthuHangNgay(timeDto);
    }

    /**
     * Thống kê doanh thu or số lượng theo quý
     */
    @GetMapping("/sumCountOrder/quarter")
    public StatisticsDTO thongkeDoanhThuOrSoluongTheoQuy(@RequestParam Long type) {
        // 0 doanh thu theo quy
        // 1 don hang thoe quy
        return statisticsService.thongkeDoanhThuTheoQuy(type);
    }

}
