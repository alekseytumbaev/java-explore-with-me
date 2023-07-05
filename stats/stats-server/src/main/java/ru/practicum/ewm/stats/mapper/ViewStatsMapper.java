package ru.practicum.ewm.stats.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.dto.ViewStats;

@UtilityClass
public class ViewStatsMapper {

    public ViewStats toViewStats(Object[] stats) {
        return new ViewStats(
                (String) stats[0],
                (String) stats[1],
                (Long) stats[2]
        );
    }
}
