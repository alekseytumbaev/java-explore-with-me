package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Краткая информация о событии
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "EventShortDto", description = "Краткая информация о событии")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class EventShortDto {

    @JsonProperty("annotation")
    private String annotation;

    @JsonProperty("category")
    private CategoryDto category;

    @JsonProperty("confirmedRequests")
    private Long confirmedRequests;

    @JsonProperty("eventDate")
    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime eventDate;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("initiator")
    private UserShortDto initiator;

    @JsonProperty("paid")
    private Boolean paid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("views")
    private Long views;

    public EventShortDto annotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    /**
     * Краткое описание
     *
     * @return annotation
     */
    @NotNull
    @Schema(name = "annotation", example = "Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории", description = "Краткое описание", requiredMode = Schema.RequiredMode.REQUIRED)
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public EventShortDto category(CategoryDto category) {
        this.category = category;
        return this;
    }

    /**
     * Get category
     *
     * @return category
     */
    @NotNull
    @Valid
    @Schema(name = "category", requiredMode = Schema.RequiredMode.REQUIRED)
    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public EventShortDto confirmedRequests(Long confirmedRequests) {
        this.confirmedRequests = confirmedRequests;
        return this;
    }

    /**
     * Количество одобренных заявок на участие в данном событии
     *
     * @return confirmedRequests
     */

    @Schema(name = "confirmedRequests", example = "5", description = "Количество одобренных заявок на участие в данном событии", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getConfirmedRequests() {
        return confirmedRequests;
    }

    public void setConfirmedRequests(Long confirmedRequests) {
        this.confirmedRequests = confirmedRequests;
    }

    public EventShortDto eventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    /**
     * Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")
     *
     * @return eventDate
     */
    @NotNull
    @Schema(name = "eventDate", example = "2024-12-31 15:10:05", description = "Дата и время на которые намечено событие (в формате \"yyyy-MM-dd HH:mm:ss\")", requiredMode = Schema.RequiredMode.REQUIRED)
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public EventShortDto id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Идентификатор
     *
     * @return id
     */

    @Schema(name = "id", example = "1", description = "Идентификатор", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventShortDto initiator(UserShortDto initiator) {
        this.initiator = initiator;
        return this;
    }

    /**
     * Get initiator
     *
     * @return initiator
     */
    @NotNull
    @Valid
    @Schema(name = "initiator", requiredMode = Schema.RequiredMode.REQUIRED)
    public UserShortDto getInitiator() {
        return initiator;
    }

    public void setInitiator(UserShortDto initiator) {
        this.initiator = initiator;
    }

    public EventShortDto paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    /**
     * Нужно ли оплачивать участие
     *
     * @return paid
     */
    @NotNull
    @Schema(name = "paid", example = "true", description = "Нужно ли оплачивать участие", requiredMode = Schema.RequiredMode.REQUIRED)
    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public EventShortDto title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Заголовок
     *
     * @return title
     */
    @NotNull
    @Schema(name = "title", example = "Знаменитое шоу 'Летающая кукуруза'", description = "Заголовок", requiredMode = Schema.RequiredMode.REQUIRED)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EventShortDto views(Long views) {
        this.views = views;
        return this;
    }

    /**
     * Количество просмотрев события
     *
     * @return views
     */

    @Schema(name = "views", example = "999", description = "Количество просмотрев события", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventShortDto eventShortDto = (EventShortDto) o;
        return Objects.equals(this.annotation, eventShortDto.annotation) &&
                Objects.equals(this.category, eventShortDto.category) &&
                Objects.equals(this.confirmedRequests, eventShortDto.confirmedRequests) &&
                Objects.equals(this.eventDate, eventShortDto.eventDate) &&
                Objects.equals(this.id, eventShortDto.id) &&
                Objects.equals(this.initiator, eventShortDto.initiator) &&
                Objects.equals(this.paid, eventShortDto.paid) &&
                Objects.equals(this.title, eventShortDto.title) &&
                Objects.equals(this.views, eventShortDto.views);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, confirmedRequests, eventDate, id, initiator, paid, title, views);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EventShortDto {\n");
        sb.append("    annotation: ").append(toIndentedString(annotation)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    confirmedRequests: ").append(toIndentedString(confirmedRequests)).append("\n");
        sb.append("    eventDate: ").append(toIndentedString(eventDate)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    initiator: ").append(toIndentedString(initiator)).append("\n");
        sb.append("    paid: ").append(toIndentedString(paid)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    views: ").append(toIndentedString(views)).append("\n");
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

