package ru.practicum.ewm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class StatsClient {
    private static final Logger log = LoggerFactory.getLogger(StatsClient.class);

    private static final String STATS_SERVER_URI = "http://localhost:9090";
    private static final String HIT_URL = STATS_SERVER_URI + "/hit";
    private static final String STATS_URL = STATS_SERVER_URI + "/stats";

    private final RestTemplate template = new RestTemplate();

    /**
     * Save statistics about request. Logs {@link RestClientException} on fail
     */
    public void saveStats(EndpointHit endpointHit) {
        try {
            template.postForEntity(HIT_URL, endpointHit, Void.class);
        } catch (RestClientException e) {
            log.error("Failed to save statistics {}. Exception: ", endpointHit, e);
        }
    }

    /**
     * Getting statistics on visits. Logs {@link RestClientException} on fail
     *
     * @param start  start of the time range
     * @param end    end of the time range
     * @param uris   uris to get stats, pass empty list to get all stats
     * @param unique count only unique ips if true
     */
    public Optional<ViewStats[]> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        uris = uris == null || uris.isEmpty() ? new ArrayList<>() : uris;

        Map<String, Object> params = new HashMap<>();
        //не использовал URLEncoder как написано в задании, так как RestTemplate сам кодирует
        params.put("start", start.format(ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.put("end", end.format(ofPattern("yyyy-MM-dd HH:mm:ss")));
        params.put("unique", unique);
        uris.forEach(uri -> params.put("uris", uri));

        ResponseEntity<ViewStats[]> response;
        try {
            response = template.getForEntity(STATS_URL + "?start={start}&end={end}&uris={uris}&unique={unique}",
                    ViewStats[].class, params);
            if (response.getBody() == null) {
                return Optional.empty();
            }
        } catch (RestClientException e) {
            log.error("Failed to get statistics, start: {}, end: {}, uris: {}, unique: {}. Exception: ",
                    start, end, uris, unique, e);
            return Optional.empty();
        }

        return Optional.of(response.getBody());
    }
}
