package ru.practicum.ewm.stats.mapper;

import ru.practicum.ewm.stats.dto.ViewStats;

public class ViewStatsMapper {

    public static ViewStats toViewStats(Object[] stats) {
        return new ViewStats(
                (String) stats[0],
                (String) stats[1],
                (Long) stats[2]
        );
    }
}
