package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.annotation.Generated;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Заявка на участие в событии
 */

@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ParticipationRequestDto", description = "Заявка на участие в событии")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-10T18:11:12.435594+07:00[Asia/Barnaul]")
public class ParticipationRequestDto {

    @JsonProperty("created")
    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime created;

    @JsonProperty("event")
    private Long event;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("requester")
    private Long requester;

    @JsonProperty("status")
    private ParticipationRequestStatus status;

    public ParticipationRequestDto created(LocalDateTime created) {
        this.created = created;
        return this;
    }

    /**
     * Дата и время создания заявки
     *
     * @return created
     */

    @Schema(name = "created", example = "2022-09-06T21:10:05.432", description = "Дата и время создания заявки", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ParticipationRequestDto event(Long event) {
        this.event = event;
        return this;
    }

    /**
     * Идентификатор события
     *
     * @return event
     */

    @Schema(name = "event", example = "1", description = "Идентификатор события", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public ParticipationRequestDto id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Идентификатор заявки
     *
     * @return id
     */

    @Schema(name = "id", example = "3", description = "Идентификатор заявки", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParticipationRequestDto requester(Long requester) {
        this.requester = requester;
        return this;
    }

    /**
     * Идентификатор пользователя, отправившего заявку
     *
     * @return requester
     */

    @Schema(name = "requester", example = "2", description = "Идентификатор пользователя, отправившего заявку", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getRequester() {
        return requester;
    }

    public void setRequester(Long requester) {
        this.requester = requester;
    }

    public ParticipationRequestDto status(ParticipationRequestStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Статус заявки
     *
     * @return status
     */

    @Schema(name = "status", example = "PENDING", description = "Статус заявки", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public ParticipationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ParticipationRequestStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationRequestDto participationRequestDto = (ParticipationRequestDto) o;
        return Objects.equals(this.created, participationRequestDto.created) &&
                Objects.equals(this.event, participationRequestDto.event) &&
                Objects.equals(this.id, participationRequestDto.id) &&
                Objects.equals(this.requester, participationRequestDto.requester) &&
                Objects.equals(this.status, participationRequestDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, event, id, requester, status);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ParticipationRequestDto {\n");
        sb.append("    created: ").append(toIndentedString(created)).append("\n");
        sb.append("    event: ").append(toIndentedString(event)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    requester: ").append(toIndentedString(requester)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

