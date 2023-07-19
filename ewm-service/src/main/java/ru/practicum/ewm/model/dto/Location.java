package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import java.util.Objects;

/**
 * Широта и долгота места проведения события
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LocationEntity", description = "Широта и долгота места проведения события")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class Location {

    @JsonProperty("lat")
    private Float lat;

    @JsonProperty("lon")
    private Float lon;

    public Location lat(Float lat) {
        this.lat = lat;
        return this;
    }

    /**
     * Широта
     *
     * @return lat
     */

    @Schema(name = "lat", example = "55.754167", description = "Широта", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Location lon(Float lon) {
        this.lon = lon;
        return this;
    }

    /**
     * Долгота
     *
     * @return lon
     */

    @Schema(name = "lon", example = "37.62", description = "Долгота", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(this.lat, location.lat) &&
                Objects.equals(this.lon, location.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LocationEntity {\n");
        sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
        sb.append("    lon: ").append(toIndentedString(lon)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

