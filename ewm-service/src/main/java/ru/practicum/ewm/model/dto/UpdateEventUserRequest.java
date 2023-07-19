package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
 */

@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateEventUserRequest", description = "Данные для изменения информации о событии. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class UpdateEventUserRequest {

    @JsonProperty("annotation")
    private String annotation;

    @JsonProperty("category")
    private Long category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventDate")
    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime eventDate;

    @JsonProperty("location")
    private Location location;

    @JsonProperty("paid")
    private Boolean paid;

    @JsonProperty("participantLimit")
    private Integer participantLimit;

    @JsonProperty("requestModeration")
    private Boolean requestModeration;

    /**
     * Изменение сотояния события
     */
    public enum StateActionEnum {
        SEND_TO_REVIEW("SEND_TO_REVIEW"),

        CANCEL_REVIEW("CANCEL_REVIEW");

        private String value;

        StateActionEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StateActionEnum fromValue(String value) {
            for (StateActionEnum b : StateActionEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @JsonProperty("stateAction")
    private StateActionEnum stateAction;

    @JsonProperty("title")
    private String title;

    public UpdateEventUserRequest annotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    /**
     * Новая аннотация
     *
     * @return annotation
     */
    @Size(min = 20, max = 2000)
    @Schema(name = "annotation", example = "Сап прогулки по рекам и каналам – это возможность увидеть Практикбург с другого ракурса", description = "Новая аннотация", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public UpdateEventUserRequest category(Long category) {
        this.category = category;
        return this;
    }

    /**
     * Новая категория
     *
     * @return category
     */

    @Schema(name = "category", example = "3", description = "Новая категория", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public UpdateEventUserRequest description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Новое описание
     *
     * @return description
     */
    @Size(min = 20, max = 7000)
    @Schema(name = "description", example = "От английского SUP - Stand Up Paddle — \"стоя на доске с веслом\", гавайская разновидность сёрфинга, в котором серфер, стоя на доске, катается на волнах и при этом гребет веслом, а не руками, как в классическом серфинге.", description = "Новое описание", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UpdateEventUserRequest eventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    /**
     * Новые дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"
     *
     * @return eventDate
     */

    @Schema(name = "eventDate", example = "2023-10-11 23:10:05", description = "Новые дата и время на которые намечено событие. Дата и время указываются в формате \"yyyy-MM-dd HH:mm:ss\"", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public UpdateEventUserRequest location(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Get location
     *
     * @return location
     */
    @Valid
    @Schema(name = "location", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UpdateEventUserRequest paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    /**
     * Новое значение флага о платности мероприятия
     *
     * @return paid
     */

    @Schema(name = "paid", example = "true", description = "Новое значение флага о платности мероприятия", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public UpdateEventUserRequest participantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
        return this;
    }

    /**
     * Новый лимит пользователей
     *
     * @return participantLimit
     */

    @Schema(name = "participantLimit", example = "7", description = "Новый лимит пользователей", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Integer getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(Integer participantLimit) {
        this.participantLimit = participantLimit;
    }

    public UpdateEventUserRequest requestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
        return this;
    }

    /**
     * Нужна ли пре-модерация заявок на участие
     *
     * @return requestModeration
     */

    @Schema(name = "requestModeration", example = "false", description = "Нужна ли пре-модерация заявок на участие", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Boolean getRequestModeration() {
        return requestModeration;
    }

    public void setRequestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration;
    }

    public UpdateEventUserRequest stateAction(StateActionEnum stateAction) {
        this.stateAction = stateAction;
        return this;
    }

    /**
     * Изменение сотояния события
     *
     * @return stateAction
     */

    @Schema(name = "stateAction", example = "CANCEL_REVIEW", description = "Изменение сотояния события", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public StateActionEnum getStateAction() {
        return stateAction;
    }

    public void setStateAction(StateActionEnum stateAction) {
        this.stateAction = stateAction;
    }

    public UpdateEventUserRequest title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Новый заголовок
     *
     * @return title
     */
    @Size(min = 3, max = 120)
    @Schema(name = "title", example = "Сап прогулки по рекам и каналам", description = "Новый заголовок", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UpdateEventUserRequest updateEventUserRequest = (UpdateEventUserRequest) o;
        return Objects.equals(this.annotation, updateEventUserRequest.annotation) &&
                Objects.equals(this.category, updateEventUserRequest.category) &&
                Objects.equals(this.description, updateEventUserRequest.description) &&
                Objects.equals(this.eventDate, updateEventUserRequest.eventDate) &&
                Objects.equals(this.location, updateEventUserRequest.location) &&
                Objects.equals(this.paid, updateEventUserRequest.paid) &&
                Objects.equals(this.participantLimit, updateEventUserRequest.participantLimit) &&
                Objects.equals(this.requestModeration, updateEventUserRequest.requestModeration) &&
                Objects.equals(this.stateAction, updateEventUserRequest.stateAction) &&
                Objects.equals(this.title, updateEventUserRequest.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, description, eventDate, location, paid, participantLimit, requestModeration, stateAction, title);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UpdateEventUserRequest {\n");
        sb.append("    annotation: ").append(toIndentedString(annotation)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    eventDate: ").append(toIndentedString(eventDate)).append("\n");
        sb.append("    location: ").append(toIndentedString(location)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    participantLimit: ").append(toIndentedString(participantLimit)).append("\n");
        sb.append("    requestModeration: ").append(toIndentedString(requestModeration)).append("\n");
        sb.append("    stateAction: ").append(toIndentedString(stateAction)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
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

