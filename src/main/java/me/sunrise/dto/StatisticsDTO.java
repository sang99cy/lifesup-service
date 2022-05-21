package me.sunrise.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StatisticsDTO {
    private String tenSanPham;
    private Long soLuong;
    private Long doanhThu;
    private Integer quy;
    private String ngay;

    public StatisticsDTO() {
    }

    public StatisticsDTO(Long soLuong) {
        this.soLuong = soLuong;
    }

    public StatisticsDTO(Integer quy, Long doanhThu) {
        this.quy = quy;
        this.doanhThu = doanhThu;
    }

    public StatisticsDTO(Long soLuong, Integer quy) {
        this.soLuong = soLuong;
        this.quy = quy;
    }

    public StatisticsDTO(Long doanhThu, Long soLuong) {
        this.doanhThu = doanhThu;
        this.soLuong = soLuong;
    }

    public StatisticsDTO(String tenSanPham, Long soLuong, Long doanhThu) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.doanhThu = doanhThu;
    }

    public StatisticsDTO(Long doanhThu, String ngay) {
        this.doanhThu = doanhThu;
        this.ngay = ngay;
    }
}
