package me.sunrise.dto.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.sunrise.dto.StatisticsDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsResponse {
    private List<StatisticsDTO> statistics;
    private Long total;
}
