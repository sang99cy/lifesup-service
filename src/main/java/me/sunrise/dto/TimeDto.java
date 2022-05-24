package me.sunrise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeDto {
    private String createForm;
    private String createTo;
    private String productName;


    @Override
    public String toString() {
        return "TimeDto{" +
                "createForm='" + createForm + '\'' +
                ", createTo='" + createTo + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
